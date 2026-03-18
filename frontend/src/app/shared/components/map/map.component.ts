
import {Component, AfterViewInit, Inject, PLATFORM_ID, signal} from '@angular/core';
import {isPlatformBrowser, CommonModule} from '@angular/common';
import {RouterModule, ActivatedRoute} from '@angular/router';
import {LatLngExpression} from 'leaflet';
import { FinalMapService } from '../../../core/service/MapService/FinalMapService/finalMapService';
import { RiskFactorMapService } from '../../../core/service/MapService/RiskMapService/riskFactorMapService';
import { RiskFactorMapListDto } from '../../../shared/models/MapModel/RiskFactorMapModel/RiskFactorMapListDto';

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
  private onTileSelected(event:Event, mapId:number):void{
    this.map.on('click', (e:any)=>{
      console.log("\n [onTileSelected] : " + e.latlng)
      const coordinates : any = e.latlng
      const z : number = this.map.getZoom();
      const n : number = 2**z ;

      var x : number = Math.floor( n * ((coordinates.lng + 180)/360)) ;
      const lat_rad : number = coordinates.lat * (Math.PI / 180);

      var y : number = Math.floor( n * (1 - (Math.log( Math.tan(lat_rad) + (1 / Math.cos(lat_rad))) / Math.PI)) /2 );

      console.log(x,y);

      const bounds = this.tileToPolygon(x, y, z);
      this.highlightLayer = null;
      if (this.highlightLayer) {
        this.map.removeLayer(this.highlightLayer);
      }

      this.highlightLayer = this.leaflet.polygon(bounds, {
        color: 'red',
        weight: 2,
        fillOpacity: 0.1
      }).addTo(this.map);
      this.map.fitBounds(bounds);
    });
  }


  private tileToPolygon(x: number, y: number, z: number) {
    const n = Math.pow(2, z);

    const lon1 = x / n * 360 - 180;
    const lon2 = (x + 1) / n * 360 - 180;

    const lat1 = Math.atan(Math.sinh(Math.PI * (1 - 2 * y / n))) * 180 / Math.PI;
    const lat2 = Math.atan(Math.sinh(Math.PI * (1 - 2 * (y + 1) / n))) * 180 / Math.PI;

    return [
      [lat1, lon1], // top-left
      [lat1, lon2], // top-right
      [lat2, lon2], // bottom-right
      [lat2, lon1]  // bottom-left
    ];
  }


  /**
   * TODO
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
   * TODO
   * */
  private async initOpenStreetMap(){
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
   * TODO
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
   * TODO
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
   * TODO
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
