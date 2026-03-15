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

  riskLevels = [
    {label: 'Low', color: '#2ecc71'},
    {label: 'Medium', color: '#f39c12'},
    {label: 'High', color: '#e74c3c'}];

  selectedDistrict = signal<any>(null);
  marker: any = null;
  mapTitle = signal<string>('');
  mapDescription = signal<string>('');
  riskFactorMaps = signal<RiskFactorMapListDto[]>([]);

  private map: any = null;
  private leaflet: any = null;
  private tileLayer: any = null;
  private geoJsonLayer: any = null;

   CAMEROON_COORDINATES:LatLngExpression[] = [[6.8, 12.38]];
   CAMEROON_ZOOM:number = 6.6;

  constructor(
    @Inject(PLATFORM_ID) private platformId: Object,
    private mapService: FinalMapService,
    private riskFactorMapService: RiskFactorMapService,
    private route: ActivatedRoute
    ){}

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
* Display the map OSM thanks to Leaflet on Cameron
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
              if (this.selectedDistrict() === feature.properties) {
                this.marker.remove();
                this.marker = null;
                this.selectedDistrict.set(null);
                return;
              }
              this.selectedDistrict.set(feature.properties);
              if (this.marker) this.marker.remove();
              this.marker = this.leaflet.circleMarker(e.latlng, {
                radius: 5,
                color: '#1356eb',
                fillColor: '#1959e6',
                fillOpacity: 0.8,
                pane: 'markerPane',
              }).addTo(this.map);
            });
          }
        }).addTo(this.map);

        this.map.fitBounds(this.geoJsonLayer.getBounds());
      },
      error: (err) => console.error('Failed to load map data', err)
    });
  }

  private getRiskColor(riskClass: string): string {
    for (const level of this.riskLevels) {
      if (level.label === riskClass){
        return level.color;
        }
      }
    return '#aaaaaa';
  }
}
