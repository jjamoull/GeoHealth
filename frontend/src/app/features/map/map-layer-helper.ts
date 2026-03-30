import { getRiskColor } from './map-utils';
import { MapOption } from './map-types';

export class MapLayerHelper {

  private leaflet: any = null;
  private map: any = null;
  private geoJsonLayer: any = null;
  private tileLayer: any = null;
  private marker: any = null;

  async initMap(elementId: string, center: any, zoom: number): Promise<void> {
    const L = await import('leaflet');
    this.leaflet = L.default ?? L;

    this.map = this.leaflet.map(elementId).setView(center, zoom);

    this.map.createPane('markerPane');
    this.map.getPane('markerPane').style.zIndex = 400;

    this.leaflet.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap'
    }).addTo(this.map);
  }

  applyDivisionsLayer(geoJsonString: string, onDivisionClick: (event: any) => void): void {
    const geoJson = JSON.parse(geoJsonString);

    this.geoJsonLayer = this.leaflet.geoJSON(geoJson, {
      style: (feature: any) => ({
        color: '#414241',
        weight: 1,
        fillColor: getRiskColor(feature?.properties?.Risk_categ),
        fillOpacity: 0.5,
      }),
      onEachFeature: (feature: any, layer: any) => {
        layer.on('mouseover', () => layer.setStyle({ weight: 2 }));
        layer.on('mouseout', () => layer.setStyle({ weight: 1 }));
        layer.on('click', (e: any) => {
          onDivisionClick({ properties: feature.properties, latlng: e.latlng });
        });
      }
    }).addTo(this.map);

    this.map.fitBounds(this.geoJsonLayer.getBounds());
  }

  switchTo(option: MapOption): void {
    this.clearTileLayer();
    if (option.kind === 'divisions') {
      this.geoJsonLayer?.setStyle({ fillOpacity: 0.5 });
    } else {
      this.geoJsonLayer?.setStyle({ fillOpacity: 0 });
      this.tileLayer = this.leaflet.tileLayer(
        `/tile/file/${option.id}/{z}/{x}/{y}.png`,
        { opacity: 0.7, zIndex: 500 }
      ).addTo(this.map);
    }
  }

  placeMarker(latlng: any): void {
    this.clearMarker();
    this.marker = this.leaflet.circleMarker(latlng, {
      radius: 5,
      color: '#1356eb',
      fillColor: '#1959e6',
      fillOpacity: 0.8,
      pane: 'markerPane',
    }).addTo(this.map);
  }

  clearMarker(): void {
    if (this.marker) {
      this.marker.remove();
      this.marker = null;
    }
  }

  clearTileLayer(): void {
    if (this.tileLayer) {
      this.tileLayer.remove();
      this.tileLayer = null;
    }
  }
}
