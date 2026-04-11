import {Component, AfterViewInit, Inject, PLATFORM_ID, signal, ChangeDetectorRef, computed} from '@angular/core';
import {isPlatformBrowser, CommonModule} from '@angular/common';
import {RouterModule, ActivatedRoute} from '@angular/router';
import {LatLngExpression} from 'leaflet';
import { FinalMapService } from '../../core/service/MapService/FinalMapService/finalMapService';
import { RasterMapService } from '../../core/service/MapService/RasterService/RasterMapService';
import { RasterMapListDto } from '../../shared/models/MapModel/RasterMapModel/RasterMapListDto';
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
import {CAMEROON_COORDINATES} from './map.constants';
import {CAMEROON_ZOOM} from './map.constants';
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

  riskLevels = RISK_LEVELS;

  mapId: number = -1;
  isAdmin:boolean=false;
  selectedDivision = signal<any>(null);
  mapTitle = signal<string>('');
  mapDescription = signal<string>('');
  rasterMaps = signal<RasterMapListDto[]>([]);
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
    private rasterMapService: RasterMapService,
    private evaluationFormService: EvaluationFormService,
    private adminEvaluationFormService:AdminEvaluationFormService,
    private cdr: ChangeDetectorRef,
    private route: ActivatedRoute,
    private measureService: MeasureService,
    ){}

  /**
   * TODO
   */
  onOpenEvaluation(): void {
    this.showEvaluationModal.set(true);
  }

  /**
   * TODO
   */
  onCloseEvaluation(): void {
    this.showEvaluationModal.set(false);
    this.getAllForm(this.isAdmin,this.mapId);
    this.cdr.detectChanges();
  }

  /**
   * TODO
   */
  onDeleteEvaluation(): void {
    if (!confirm('Are you sure you want to delete this evaluation?')) return;

    this.evaluationFormService.deleteForm(this.existingForm()!.id).subscribe({
      next: () => {
        this.existingForm.set(null);
        this.getAllForm(this.isAdmin, this.mapId);
        },
      error: (err) => {
          console.error('Failed to delete evaluation form', err);
      }
    });
  }

  /**
   * Method called when the user selects a risk factor map
   * (or division only by default) to adapt the map displayed
   *
   * @param event :
   *    - division only : if no risk factors were selected or division only selected
   *    - "name of risk factor map" : otherwise
   * */
  onMapSelected(event: Event): void {
    const value : string = (event.target as HTMLSelectElement).value;

    if (!value || value == 'Division only') { // if value is empty or equal Division only
      this.mapHelper.switchTo({ id: null, kind: 'divisions', title: 'Risk Overview - Divisions' });
      return;
    }

    // If the user write the name of the risk factor instead of its ID
    const findWordForRiskFactor = this.rasterMaps().find(
      map => map.title == value || map.id == Number(value)
    );

    if (findWordForRiskFactor) { // findWordForRiskFactor is not empty
      this.mapHelper.switchTo({ id: findWordForRiskFactor.id, kind: 'tile', title: '' });
      return;
    } else {
      this.mapHelper.switchTo({ id: null, kind: 'divisions', title: 'Risk Overview - Divisions' });
      return;
    }

  }


  /**
    * Display the map OSM thanks to Leaflet on Cameron and load the evaluation forms
  */
  async ngAfterViewInit(): Promise<void> {
    if (!isPlatformBrowser(this.platformId)) return;
    this.loadAvailableMaps();
    this.mapId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadUserRole();
    await this.mapHelper.initMap('map', CAMEROON_COORDINATES[0], CAMEROON_ZOOM, 6, 12);
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

  /**
   * TODO
   */
  private loadAvailableMaps(): void {
    this.rasterMapService.getRiskFactors().subscribe({
          next: (maps:RasterMapListDto[]) => {
            this.rasterMaps.set(maps);
          },
          error: (err) => {
            console.error('Failed to load risk factor maps', err);
          }
    });
  }


  /**
   * TODO
   */
  private loadUserRole(): void {
    this.usersServices.isAdmin().subscribe(
    bool =>{
      this.isAdmin=bool;
      this.getAllForm(bool,this.mapId)
    });
  }

  /**
   * TODO
   */
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

  /**
   * TODO and PLEASE : It's better to have several methods than one big one… Not easy to discover this method and understand it
   */
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
        this.krippendorff.set(krippendorff)
      },
      error: (err) => {
        console.log('Failed to load krippendorff', err);
      }
    });
  }

  /**
   * TODO
   */
  getRiskColor(riskClass: string): string {
    return getRiskColor(riskClass);
  }
}
