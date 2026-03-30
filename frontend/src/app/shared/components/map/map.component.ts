
import {Component, AfterViewInit, Inject, PLATFORM_ID, signal} from '@angular/core';
import {isPlatformBrowser, CommonModule} from '@angular/common';
import {RouterModule, ActivatedRoute} from '@angular/router';
import {LatLngExpression} from 'leaflet';
import { FinalMapService } from '../../../core/service/MapService/FinalMapService/finalMapService';
import { RiskFactorMapService } from '../../../core/service/MapService/RiskMapService/riskFactorMapService';
import { RiskFactorMapListDto } from '../../../shared/models/MapModel/RiskFactorMapModel/RiskFactorMapListDto';
import {TileMeanAndXYdto} from '../../../shared/models/MapModel/RiskFactorMapModel/TileMeanAndXYdto';

@Component({
  selector: 'app-map',
  imports: [RouterModule, CommonModule],
  templateUrl: './map.component.html',
  styleUrl: './map.component.css',
  standalone: true,
})
export class MapComponent implements AfterViewInit {

  riskLevels:({label: string; color: string }[]) = [
    {label: 'Low', color: '#2ecc71'},
    {label: 'Medium', color: '#f39c12'},
    {label: 'High', color: '#e74c3c'}
  ];

  selectedDistrict = signal<any>(null);
  marker: any = null;
  mapTitle = signal<string>('');
  mapDescription = signal<string>('');
  riskFactorMaps = signal<RiskFactorMapListDto[]>([]);

  private map: any = null;
  private leaflet: any = null;
  private tileLayer: any = null;
  private geoJsonLayer: any = null;
  private highlightLayer: any = null;

  private readonly CAMEROON_COORDINATES:LatLngExpression[] = [[6.8, 12.38]];
  private readonly CAMEROON_ZOOM:number = 6.6;

  constructor(
    @Inject(PLATFORM_ID) private readonly platformId: Object,
    private mapService: FinalMapService,
    private riskFactorMapService: RiskFactorMapService,
    private route: ActivatedRoute
    ){}

  /**
   * Display the map OSM thanks to Leaflet on Cameroon
   */
  ngAfterViewInit(): void {
    if (!isPlatformBrowser(this.platformId))return;

    this.getAllRiskFactorMapForDropdown();
    this.initOpenStreetMap();
    this.setFinalRiskMap();
  }


  /**
   * TODO
   * */
  protected onMapSelected(event: Event): void {
    const value = (event.target as HTMLSelectElement).value;
    if (this.tileLayer) {
      this.tileLayer.remove();
      this.tileLayer = null;
    }

    if (!value) {
      if (this.geoJsonLayer) this.geoJsonLayer.setStyle({ fillOpacity: 0.5 });
      return;
    }

    if (this.geoJsonLayer){
      this.geoJsonLayer.setStyle({ fillOpacity: 0 });
      }

    const mapId = Number(value);
    this.tileLayer = this.leaflet.tileLayer(
      `/tile/file/${mapId}/{z}/{x}/{y}.png`,
      { opacity: 0.7, zIndex: 500 }

    ).addTo(this.map);

    this.onTileSelected(event, mapId);
  }


  /**
   * TODO
   * */
  private async onTileSelected(event:Event, mapId:number):Promise<void>{
    this.map.on('click', async (e:any)=>{
      console.log("\n [onTileSelected] : " + e.latlng)
      const coordinates : any = e.latlng
      const z : number = this.map.getZoom();


      console.log(mapId);
      console.log(coordinates.lat, coordinates.lng);
      const blockData : TileMeanAndXYdto | null = await this.getTileMean(mapId, z, coordinates.lat, coordinates.lng);


      if (blockData){
        console.log(blockData.mean);
        const bounds = this.tileToPolygon(blockData.tileX, blockData.tileY, z, blockData.blockX, blockData.blockY);

        if (this.highlightLayer) {
          this.map.removeLayer(this.highlightLayer);
        }


        this.highlightLayer = this.leaflet.polygon(bounds, {
          color: 'red',
          weight: 2,
          fillOpacity: 0.1
        }).addTo(this.map);
        //this.map.fitBounds(bounds);
    }
    });
  }

  private async getTileMean(mapId: number,
                            z : number,
                            lat : number,
                            lng : number){
    try {
      const response: Response = await fetch(`tile/file/mean/${mapId}/${z}/${lat}/${lng}`);

      if (!response.ok) {
        throw new Error("Server request failed to obtain mean tile ");
      }
      const data: TileMeanAndXYdto = await response.json();
      return data;
    }
    catch (err) {
      console.error(err);
      return null;
    }
  }


  private tileToPolygon(x: number, y: number, z: number, blockX : number, blockY : number) {
    const TILE_SIZE = 256;
    const BLOCK_SIZE = 16;

    const pixelX1 = x * TILE_SIZE + blockX * BLOCK_SIZE;
    const pixelY1 = y * TILE_SIZE + blockY * BLOCK_SIZE;
    const pixelX2 = pixelX1 + BLOCK_SIZE;
    const pixelY2 = pixelY1 + BLOCK_SIZE;

    const n = Math.pow(2, z);
    const worldSize = TILE_SIZE *n;

    const lon1 = pixelX1/worldSize*360-180;
    const lon2 = pixelX2 /worldSize *360-180;

    const lat1 = Math.atan(Math.sinh(Math.PI * (1-2 * pixelY1/ worldSize))) *180 / Math.PI;
    const lat2 = Math.atan(Math.sinh(Math.PI*(1-2 *pixelY2/worldSize)))* 180/ Math.PI;

    return [
      [lat1, lon1], // top-left
      [lat1, lon2], // top-right
      [lat2, lon2], // bottom-right
      [lat2, lon1]  // bottom-left
    ];
  }


  /**
   * get all risk factor map for dropdown that the user will be able to select
   * */
  private getAllRiskFactorMapForDropdown(){
    this.riskFactorMapService.getAllMaps().subscribe({
      next: (maps:RiskFactorMapListDto[]) => {
        this.riskFactorMaps.set(maps);
      },
      error: (err) => {
        console.error('Failed to load risk factor maps', err);
      }
    });
  }

  /**
   * Init the OpenStreetMap
   *
   * Parameters of the map :
   *    - Zoom from 6 to 12
   *    - Start on the Cameroon coordinates and zoom
   * */
  private async initOpenStreetMap(): Promise<void>{
    const L = await import('leaflet');
    this.leaflet = L.default ?? L;

    this.map = this.leaflet.map('map').setView(this.CAMEROON_COORDINATES[0], this.CAMEROON_ZOOM);

    this.map.createPane('markerPane');
    this.map.getPane('markerPane').style.zIndex = 400;
    this.map.setMinZoom(6);
    this.map.setMaxZoom(12);

    this.leaflet.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap'
    }).addTo(this.map);
  }


  /**
   * TODO
   * */
  private setFinalRiskMap(){
    const id = Number(this.route.snapshot.paramMap.get('id'));

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
              this.selectOrNotDistrict(feature);
              this.displayMarker(e);
            });
          }
        }).addTo(this.map);

        this.map.fitBounds(this.geoJsonLayer.getBounds());
      },
      error: (err) => console.error('Failed to load map data', err)
    });
  }



  /**
   * Allow users to select a district if they click on it
   *
   * (Work only if the user took the dropdown with "district … " and not a risk factor)
   *
   * */
  private selectOrNotDistrict(feature:any){
    //select again => remove marker
    if (this.selectedDistrict() === feature.properties) {
      this.marker.remove();
      this.marker = null;
      this.selectedDistrict.set(null);
      return;
    }
    this.selectedDistrict.set(feature.properties);
    if (this.marker){
      this.marker.remove();
    }
  }


  /**
   * Display on the map a blue circle marker where we click
   * */
  private displayMarker(e:any){
    //marker when we click on a district
    this.marker = this.leaflet.circleMarker(e.latlng, {
      radius: 5,
      color: '#1356eb',
      fillColor: '#1959e6',
      fillOpacity: 0.8,
      pane: 'markerPane',
    }).addTo(this.map);
  }


  /**
   * Return the color in format " # … " on the point we clicked
   * */
  private getRiskColor(riskClass: string): string {
    for (const level of this.riskLevels) {
      if (level.label === riskClass){
        return level.color;
        }
      }
    return '#dc0a0a';
  }

}
