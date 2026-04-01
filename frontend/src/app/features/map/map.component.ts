import {Component, AfterViewInit, Inject, PLATFORM_ID, signal, ChangeDetectorRef} from '@angular/core';
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
import {AdminEvaluationFormService} from '../../core/service/AdminService/AdminEvaluationFormService/AdminEvaluationFormService';
import {AdminResponseEvaluationFormDto} from '../../shared/models/AdminModel/EvaluationFormModel/AdminResponseEvaluationFormDto';
import { MapLayerHelper } from './map-layer-helper';
import { RISK_LEVELS, getRiskColor } from './map-utils';
import{MeasureService} from '../../core/service/MeasureService/measureService';
import {DivisionRiskDto} from '../../shared/models/MeasureModel/DivisionRiskDto';
import {TooltipDescriptionComponent} from '../../shared/components/tooltip-description/tooltip-description';

@Component({
  selector: 'app-map',
  imports: [RouterModule, CommonModule, MapLegendComponent, ButtonComponent, EvaluationModalComponent, EvaluationCommentComponent, TooltipDescriptionComponent],
  templateUrl: './map.component.html',
  styleUrl: './map.component.css',
  standalone: true,
})
export class MapComponent implements AfterViewInit {

  CAMEROON_COORDINATES:LatLngExpression[] = [[6.8, 12.38]];
  CAMEROON_ZOOM:number = 6.6;
  riskLevels = RISK_LEVELS;

  mapId: number = -1;
  isAdmin:boolean=false;
  selectedDivision = signal<any>(null);
  mapTitle = signal<string>('');
  mapDescription = signal<string>('');
  riskFactorMaps = signal<RiskFactorMapListDto[]>([]);
  showEvaluationModal = signal<boolean>(false);
  existingForm = signal<ResponseEvaluationFormDto | null>(null);
  allEvaluationFormsUser= signal<ResponseEvaluationFormDto[]>([]);
  allEvaluationFormsAdmin= signal<AdminResponseEvaluationFormDto[]>([]);
  allDivisions = signal<{ name: string, risk: string}[]>([]);
  weightedEntropy = signal<number | null>(null);
  globalConsensusIndex = signal<number | null>(null);
  krippendorff= signal<number | null>(null);

  private mapHelper = new MapLayerHelper();

  constructor(
    @Inject(PLATFORM_ID) private platformId: Object,
    private mapService: FinalMapService,
    private usersServices: UsersServices,
    private riskFactorMapService: RiskFactorMapService,
    private evaluationFormService: EvaluationFormService,
    private adminEvaluationFormService:AdminEvaluationFormService,
    private cdr: ChangeDetectorRef,
    private route: ActivatedRoute,
    private measureService: MeasureService,
    ){}

  onOpenEvaluation(): void {
    this.showEvaluationModal.set(true);
  }

  onCloseEvaluation(): void {
    this.showEvaluationModal.set(false);
    this.existingForm.set(null);
    this.selectedDivision.set(null);
    this.mapHelper.clearMarker();
    this.getAllForm(this.isAdmin,this.mapId);
    this.cdr.detectChanges();
  }

  onMapSelected(event: Event): void {
    const value = (event.target as HTMLSelectElement).value;
    if (!value) {
      this.mapHelper.switchTo({ id: null, kind: 'divisions', title: 'Risk Overview - Divisions' });
      return;
    }
    this.mapHelper.switchTo({ id: Number(value), kind: 'tile', title: '' });
  }


/**
* Display the map OSM thanks to Leaflet on Cameron and load the evaluation forms
*/
  async ngAfterViewInit(): Promise<void> {
    if (!isPlatformBrowser(this.platformId)) return;
    this.loadAvailableMaps();
    this.mapId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadUserRole();
    await this.mapHelper.initMap('map', this.CAMEROON_COORDINATES[0], this.CAMEROON_ZOOM);
    this.loadBaseMap();
  }


  /**
   * Get the evaluation form for a specific map
   *
   * @param isAdmin true if the connected user is an admin, false otherwise
   * @param mapId the id of the map you are interested in
   */
  private getAllForm(isAdmin:boolean,mapId:number){
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

  private loadAvailableMaps(): void {
    this.riskFactorMapService.getAllMaps().subscribe({
          next: (maps:RiskFactorMapListDto[]) => {
            this.riskFactorMaps.set(maps);
          },
          error: (err) => {
            console.error('Failed to load risk factor maps', err);
          }
    });
  }

  private loadUserRole(): void {
    this.usersServices.isAdmin().subscribe(
    bool =>{
      this.isAdmin=bool;
      this.getAllForm(bool,this.mapId)
    });
  }

  private loadBaseMap(): void {
    this.mapService.getMap(this.mapId).subscribe({
      next: (mapData) => {
        this.mapTitle.set(mapData.title);
        this.mapDescription.set(mapData.description);

        const geoJson = JSON.parse(mapData.fileGeoJson);
        const divisions: { name: string, risk: string }[] = [];

        for (const feature of geoJson.features) {
          divisions.push({
            name: feature.properties.NAME_2,
            risk: feature.properties.Risk_categ
          });
        }
        this.allDivisions.set(divisions);

        this.mapHelper.applyDivisionsLayer(mapData.fileGeoJson, (event) => {
          this.onDivisionClicked(event);
            });
          },
      error: (err) => console.error('Failed to load map data', err)
    });
  }

  private onDivisionClicked(event: { properties: any, latlng: any }): void {
    if (this.selectedDivision() === event.properties) {
      this.selectedDivision.set(null);
      this.existingForm.set(null);
      this.weightedEntropy.set(null);
      this.globalConsensusIndex.set(null);
      this.mapHelper.clearMarker();
      return;
    }
    this.selectedDivision.set(event.properties);
    this.mapHelper.placeMarker(event.latlng);
    this.evaluationFormService.getMyFormForADiv(this.mapId, event.properties.NAME_2).subscribe({
      next: (form) => this.existingForm.set(form),
      error: () => this.existingForm.set(null)
    });

    this.measureService.getWeightedEntropy(this.mapId, this.selectedDivision().NAME_2, this.selectedDivision().Risk_categ).subscribe({
        next: (weightedEntropy: number) => {
          this.weightedEntropy.set(weightedEntropy);
          },
        error: (err) => {
          console.log('Failed to load weightedEntropy', err);
          }
        })

    const divisionRiskDto: DivisionRiskDto = {
      divisionRiskLevel: Object.fromEntries(
        this.allDivisions().map(d => [d.name, d.risk])
      )
    };

    this.measureService.getGlobalConsensusIndex(this.mapId, divisionRiskDto).subscribe({
      next:(globalConcensusIndex: number) => {
        this.globalConsensusIndex.set(globalConcensusIndex)
        },
      error: (err) => {
        console.log('Failed to load globalConsensusIndex', err);
        }
      });

    this.measureService.getKrippendorff(this.mapId).subscribe({
      next:(krippendorff: number) => {
      },
      error: (err) => {
        console.log('Failed to load krippendorff', err);
      }
    });
  }

  getRiskColor(riskClass: string): string {
    return getRiskColor(riskClass);
  }
}
