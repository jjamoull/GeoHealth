
import { Component, AfterViewInit, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import {LatLngExpression} from 'leaflet';

@Component({
  selector: 'app-map',
  imports: [],
  templateUrl: './map.html',
  styleUrl: './map.css',
  standalone: true,
})
export class Map implements AfterViewInit {

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

  CAMEROON_COORDINATES:LatLngExpression[] = [[6.8, 12.38]];
  CAMEROON_ZOOM:number = 6.6;


/**
* Display the map OSM thanks to Leaflet on Cameron
*/
  async ngAfterViewInit() {
    if (isPlatformBrowser(this.platformId)) {
      const L = await import('leaflet');

      const map = L.map('map').setView(this.CAMEROON_COORDINATES[0], this.CAMEROON_ZOOM);
      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap'
      }).addTo(map);
    }
  }


}
