import {AfterViewInit, Component, Input} from '@angular/core';
import {CAMEROON_COORDINATES} from '../map/map.constants';
import {FinalMapService} from '../../core/service/MapService/FinalMapService/finalMapService';
import {MapLayerHelper} from '../map/map-layer-helper';


let counterID:number = 0;

@Component({
  selector: 'app-map-preview',
  imports: [],
  templateUrl: './map-preview-component.html',
  styleUrl: './map-preview-component.css',
})
export class MapPreviewComponent implements AfterViewInit {

  @Input() mapId!: number;

  // Allow to have a unique id due to all maps get from teh "for" in navigation page
  mapIdHtml = 'map-preview-' + (counterID++);

  private mapHelper = new MapLayerHelper();

  /*
  * minZoom and maxZoom are just necessary for method initMapHelper() that use initMap()
  * that requires 5 parameters. You can modify and put any numbers you want if you
  * always consider that the user can't interact with the preview map.
  * */
  private minZoom : number = 1;
  private maxZoom : number = 20;
  private cameroonZoom: number = 5;


  constructor(private mapService: FinalMapService) {}



  async ngAfterViewInit() {
    // avoid to init the map before the DOM did his job
    setTimeout(async () => {
      await this.initMapHelper();
      this.disableInteractions();
      this.getTheMap();
    });

    await this.initMapHelper();
    this.disableInteractions();
    this.getTheMap();
  }


  /**
   * Init the map helper (OSM) with all specifications of Cameroon
   * */
  private async initMapHelper(){
    await this.mapHelper.initMap(
      this.mapIdHtml,
      CAMEROON_COORDINATES[0],
      this.cameroonZoom,
      this.minZoom,
      this.maxZoom,
      false
    );
  }

  /**
   * Get the map base on the ID from mapId
   * accompanied by the GeoJSON file to distinguish
   * all maps
   * */
  private getTheMap():void{
    this.mapService.getMap(this.mapId).subscribe({
      next: (mapData) => {
        this.mapHelper.applyDivisionsLayer(
          mapData.fileGeoJson,
          () => {},
          mapData.tags.at(0)
        );
      }
    });
  }

  /**
   * Delete or make disable some functions that leaflet
   * provides to us to make sure that the user can't interact
   * with the preview map
   * */
  private disableInteractions():void {
    const map:any = (this.mapHelper as any).map;
    map.dragging.disable();
    map.scrollWheelZoom.disable();
    map.doubleClickZoom.disable();
    map.boxZoom.disable();
    map.keyboard.disable();
    map.touchZoom.disable();
    map.zoomControl.remove();
  }

}
