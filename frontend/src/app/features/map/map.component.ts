import {Component, AfterViewInit, Inject, PLATFORM_ID, signal, OnInit, ChangeDetectorRef} from '@angular/core';
import {isPlatformBrowser, CommonModule} from '@angular/common';
import {RouterModule, ActivatedRoute} from '@angular/router';
import {LatLngExpression} from 'leaflet';
import { FinalMapService } from '../../core/service/MapService/FinalMapService/finalMapService';
import { RiskFactorMapService } from '../../core/service/MapService/RiskMapService/riskFactorMapService';
import { RiskFactorMapListDto } from '../../shared/models/MapModel/RiskFactorMapModel/RiskFactorMapListDto';
import {ButtonComponent} from '../../shared/components/button.component/button.component';
import {EvaluationModalComponent } from './evaluation-modal/evaluation-modal';

import { MapLegendComponent } from './map-legend/map-legend';
import {ResponseEvaluationFormDto} from '../../shared/models/EvaluationFormModel/ResponseEvaluationFormDto';
import {EvaluationFormService} from '../../core/service/EvaluationFormService/EvaluationFormService';
import {EvaluationCommentComponent} from './evaluation-comment/evaluation-comment';
import {UsersServices} from '../../core/service/UserService/users-services';
import {
  AdminEvaluationFormService
} from '../../core/service/AdminService/AdminEvaluationFormService/AdminEvaluationFormService';
import {
  AdminResponseEvaluationFormDto
} from '../../shared/models/AdminModel/EvaluationFormModel/AdminResponseEvaluationFormDto';

@Component({
  selector: 'app-map',
  imports: [RouterModule, CommonModule, MapLegendComponent, ButtonComponent, EvaluationModalComponent, EvaluationCommentComponent],
  templateUrl: './map.component.html',
  styleUrl: './map.component.css',
  standalone: true,
})
export class MapComponent implements  OnInit, AfterViewInit {

  riskLevels = [
    {label: 'Low', color: '#2ecc71'},
    {label: 'Medium', color: '#f39c12'},
    {label: 'High', color: '#e74c3c'}];
  mapId:number=-1;

  selectedDivision = signal<any>(null);
  marker: any = null;
  mapTitle = signal<string>('');
  mapDescription = signal<string>('');
  riskFactorMaps = signal<RiskFactorMapListDto[]>([]);
  showEvaluationModal = signal<boolean>(false);
  existingForm = signal<ResponseEvaluationFormDto | null>(null);
  allEvaluationFormsUser= signal<ResponseEvaluationFormDto[]>([]);
  allEvaluationFormsAdmin= signal<AdminResponseEvaluationFormDto[]>([]);

  private map: any = null;
  private leaflet: any = null;
  private tileLayer: any = null;
  private geoJsonLayer: any = null;

   CAMEROON_COORDINATES:LatLngExpression[] = [[6.8, 12.38]];
   CAMEROON_ZOOM:number = 6.6;

  public isAdmin:boolean=false;

  constructor(
    @Inject(PLATFORM_ID) private platformId: Object,
    private mapService: FinalMapService,
    private usersServices: UsersServices,
    private riskFactorMapService: RiskFactorMapService,
    private evaluationFormService: EvaluationFormService,
    private adminEvaluationFormService:AdminEvaluationFormService,
    private cdr: ChangeDetectorRef,
    private route: ActivatedRoute,
    ){}

  onOpenEvaluation(): void {
    this.showEvaluationModal.set(true);
  }

  onCloseEvaluation(): void {
    this.showEvaluationModal.set(false);
    this.existingForm.set(null);
    this.selectedDivision.set(null);
    if (this.marker) {
      this.marker.remove();
      this.marker = null;
    }
    this.getAllForm(this.isAdmin,this.mapId);
    this.cdr.detectChanges();
  }

  onMapSelected(event: Event): void {
    const value = (event.target as HTMLSelectElement).value;
    if (this.tileLayer) {
      this.tileLayer.remove();
      this.tileLayer = null;
    }
    if (!value) {
      if (this.geoJsonLayer) this.geoJsonLayer.setStyle({ fillOpacity: 0.5 });
      return;
    }
    const mapId = Number(value);
    if (this.geoJsonLayer){
      this.geoJsonLayer.setStyle({ fillOpacity: 0 });
      }
    this.tileLayer = this.leaflet.tileLayer(
      `/tile/file/${mapId}/{z}/{x}/{y}.png`,
      { opacity: 0.7, zIndex: 500 }
    ).addTo(this.map);
  }

  /**
   * Check whether the connected user is an admin or not
   */
  ngOnInit() {
  }

/**
* Display the map OSM thanks to Leaflet on Cameron and load the evaluation forms
*/
  async ngAfterViewInit(): Promise<void> {
    if (!isPlatformBrowser(this.platformId)) return;


    this.riskFactorMapService.getAllMaps().subscribe({
          next: (maps:RiskFactorMapListDto[]) => {
            this.riskFactorMaps.set(maps);
          },
          error: (err) => {
            console.error('Failed to load risk factor maps', err);
          }
    });

    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.mapId=id;

    this.usersServices.isAdmin().subscribe(
    bool =>{
      this.isAdmin=bool;
      this.getAllForm(bool,id)
    });


    const L = await import('leaflet');
    this.leaflet = L.default ?? L;

    this.map = this.leaflet.map('map').setView(this.CAMEROON_COORDINATES[0], this.CAMEROON_ZOOM);

    this.map.createPane('markerPane');
    this.map.getPane('markerPane').style.zIndex = 400;

    this.leaflet.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap'
    }).addTo(this.map);

    this.mapService.getMap(id).subscribe({
      next: (mapData) => {
        this.mapTitle.set(mapData.title);
        this.mapDescription.set(mapData.description);
        const geoJson = JSON.parse(mapData.fileGeoJson);
        console.log(geoJson);

        this.geoJsonLayer = this.leaflet.geoJSON(geoJson, {
          style: (feature: any) => ({
            color: '#414241',
            weight: 1,
            fillColor: this.getRiskColor(feature?.properties?.Risk_categ),
            fillOpacity: 0.5,
          }),
          onEachFeature: (feature: any, layer: any) => {
            layer.on('mouseover', () => layer.setStyle({ weight: 2 }));
            layer.on('mouseout', () => layer.setStyle({ weight: 1 }));
            layer.on('click', (e: any) => {
              if (this.selectedDivision() === feature.properties) {
                this.marker.remove();
                this.marker = null;
                this.selectedDivision.set(null);
                this.existingForm.set(null);
                return;
              }
              this.selectedDivision.set(feature.properties);
              if (this.marker) this.marker.remove();
              this.marker = this.leaflet.circleMarker(e.latlng, {
                radius: 5,
                color: '#1356eb',
                fillColor: '#1959e6',
                fillOpacity: 0.8,
                pane: 'markerPane',
              }).addTo(this.map);
              this.evaluationFormService.getMyFormForADiv(id,feature.properties.NAME_2).subscribe({
                next: (form) => this.existingForm.set(form),
                error: () => this.existingForm.set(null)
              });
            });
          }
        }).addTo(this.map);

        this.map.fitBounds(this.geoJsonLayer.getBounds());
      },
      error: (err) => console.error('Failed to load map data', err)
    });
  }


  /**
   * Get the evaluation form for a specific map
   *
   * @param isAdmin true if the connected user is an admin, false otherwise
   * @param mapId the id of the map you are interested in
   */
  public getAllForm(isAdmin:boolean,mapId:number){
    if(isAdmin){
      this.adminEvaluationFormService.getAllForm(mapId).subscribe({
        next: (evaluationForms:AdminResponseEvaluationFormDto[])=>{
          this.allEvaluationFormsAdmin.set(evaluationForms);
        },
        error: (err)=>{
          console.error('Failed to load evaluation forms', err);
        }
      })
    }
    else{
      this.evaluationFormService.getAllForm(mapId).subscribe({
        next: (evaluationForms:ResponseEvaluationFormDto[])=>{
          this.allEvaluationFormsUser.set(evaluationForms);
        },
        error: (err)=>{
          console.error('Failed to load evaluation forms', err);
        }
      })
    }
  }

  public getRiskColor(riskClass: string): string {
    for (const level of this.riskLevels) {
      if (level.label === riskClass){
        return level.color;
        }
      }
    return '#aaaaaa';
  }
}
