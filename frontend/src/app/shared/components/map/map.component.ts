import {Component, AfterViewInit, Inject, PLATFORM_ID, signal} from '@angular/core';
import {isPlatformBrowser, CommonModule} from '@angular/common';
import {RouterModule, ActivatedRoute} from '@angular/router';
import {LatLngExpression} from 'leaflet';
import { FinalMapService } from '../../../core/service/MapService/FinalMapService/finalMapService';

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

   CAMEROON_COORDINATES:LatLngExpression[] = [[6.8, 12.38]];
   CAMEROON_ZOOM:number = 6.6;

  constructor(
    @Inject(PLATFORM_ID) private platformId: Object,
    private mapService: FinalMapService,
    private route: ActivatedRoute
    ){}

/**
* Display the map OSM thanks to Leaflet on Cameron
*/
  async ngAfterViewInit(): Promise<void> {
    if (!isPlatformBrowser(this.platformId)) return;

  const id = Number(this.route.snapshot.paramMap.get('id'));

    const L = await import('leaflet');
    const leaflet = L.default ?? L;

    const map = leaflet.map('map').setView(this.CAMEROON_COORDINATES[0], this.CAMEROON_ZOOM);

    leaflet.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap'
    }).addTo(map);

    this.mapService.getMap(id).subscribe({
      next: (mapData) => {
        this.mapTitle.set(mapData.title);
        this.mapDescription.set(mapData.description);
        const geoJson = JSON.parse(mapData.fileGeoJson);
        console.log(geoJson);

        const geoJsonLayer = leaflet.geoJSON(geoJson, {
          style: (feature) => ({
            color: '#333',
            weight: 1,
            fillColor: this.getRiskColor(feature?.properties?.Risk_categ),
            fillOpacity: 0.5,
          }),
          onEachFeature: (feature, layer) => {
            layer.on('mouseover', () => {
              (layer as any).setStyle({ fillOpacity:0.7 });
            });

            layer.on('mouseout', () => {
              (layer as any).setStyle({ fillOpacity: 0.5 });
            });

            layer.on('click', (e:any) => {
              this.selectedDistrict.set(feature.properties);

              if (this.marker) {
                this.marker.remove();
                this.marker = null;
                } else {
                  this.marker = leaflet.circleMarker(e.latlng, {
                    radius: 6,
                    color: '#2563eb',
                    fillColor: '#2563eb',
                    fillOpacity: 1,
                    }).addTo(map);
                  }
            });
          }
        }).addTo(map);

        map.fitBounds(geoJsonLayer.getBounds());
      },
      error: (err) => {
        console.error('Failed to load map data', err);
      }
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
