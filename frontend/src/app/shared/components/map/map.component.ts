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

   CAMEROON_COORDINATES:LatLngExpression[] = [[6.8, 12.38]];
   CAMEROON_ZOOM:number = 6.6;

  constructor(
    @Inject(PLATFORM_ID) private platformId: Object,
    private mapService: FinalMapService,
    private riskFactorMapService: RiskFactorMapService,
    private route: ActivatedRoute
    ){}

  onMapSelected(event: Event): void {
    const mapId = Number((event.target as HTMLSelectElement).value);
    if (this.tileLayer) {
      this.tileLayer.remove();
    }
    this.tileLayer = this.leaflet.tileLayer(
      `/tile/file/${mapId}/{z}/{x}/{y}.png`
    ).addTo(this.map);
  }

/**
* Display the map OSM thanks to Leaflet on Cameron
*/
  async ngAfterViewInit(): Promise<void> {
    if (!isPlatformBrowser(this.platformId)) return;

    this.riskFactorMapService.getAllMaps().subscribe({
          next: (maps) => this.riskFactorMaps.set(maps),
          error: (err) => console.error('Failed to load risk factor maps', err)
        });

    const id = Number(this.route.snapshot.paramMap.get('id'));

    const L = await import('leaflet');
    this.leaflet = L.default ?? L;

    this.map = this.leaflet.map('map').setView(this.CAMEROON_COORDINATES[0], this.CAMEROON_ZOOM);

    this.leaflet.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap'
    }).addTo(this.map);

    this.mapService.getMap(id).subscribe({
      next: (mapData) => {
        this.mapTitle.set(mapData.title);
        this.mapDescription.set(mapData.description);
        const geoJson = JSON.parse(mapData.fileGeoJson);
        console.log(geoJson);

        const geoJsonLayer = this.leaflet.geoJSON(geoJson, {
          style: (feature: any) => ({
            color: '#333',
            weight: 1,
            fillColor: this.getRiskColor(feature?.properties?.Risk_categ),
            fillOpacity: 0.5,
          }),
          onEachFeature: (feature: any, layer: any) => {
            layer.on('mouseover', () => layer.setStyle({ fillOpacity: 0.7 }));
            layer.on('mouseout', () => layer.setStyle({ fillOpacity: 0.5 }));
            layer.on('click', (e: any) => {
              this.selectedDistrict.set(feature.properties);
              if (this.marker) {
                this.marker.remove();
                this.marker = null;
              } else {
                this.marker = this.leaflet.circleMarker(e.latlng, {
                  radius: 6,
                  color: '#2563eb',
                  fillColor: '#2563eb',
                  fillOpacity: 1,
                }).addTo(this.map);
              }
            });
          }
        }).addTo(this.map);

        this.map.fitBounds(geoJsonLayer.getBounds());
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
