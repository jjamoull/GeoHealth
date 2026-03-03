import {Component, AfterViewInit, Inject, PLATFORM_ID} from '@angular/core';
import {isPlatformBrowser} from '@angular/common';
import {RouterModule, ActivatedRoute} from '@angular/router';
import {LatLngExpression} from 'leaflet';
import { MapService } from '../../../core/service/MapService/mapService';

@Component({
  selector: 'app-map',
  imports: [RouterModule],
  templateUrl: './map.component.html',
  styleUrl: './map.component.css',
  standalone: true,
})
export class MapComponent implements AfterViewInit {

   CAMEROON_COORDINATES:LatLngExpression[] = [[6.8, 12.38]];
   CAMEROON_ZOOM:number = 6.6;

  constructor(
    @Inject(PLATFORM_ID) private platformId: Object,
    private mapService: MapService,
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
        const geoJson = JSON.parse(mapData.fileGeoJson);
        console.log(geoJson);

        const geoJsonLayer = leaflet.geoJSON(geoJson, {
          style: (feature) => ({
            color: '#333',
            weight: 1,
            fillColor: this.getRiskColor(feature?.properties?.Risk_categ),
            fillOpacity: 0.7,
          })
        }).addTo(map);

        map.fitBounds(geoJsonLayer.getBounds());
      },
      error: (err) => {
        console.error('Failed to load map data', err);
      }
    });
  }

  private getRiskColor(riskClass: string): string {
    switch (riskClass) {
      case 'Low':    return '#2ecc71';
      case 'Medium': return '#f39c12';
      case 'High':   return '#e74c3c';
      default:       return '#aaaaaa';
    }
  }
}
