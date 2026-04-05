import { getRiskColor } from './map-utils';
import { MapOption } from './map-types';
import { TileMeanAndXYdto } from '../../shared/models/MapModel/RiskFactorMapModel/TileMeanAndXYdto';

export class MapLayerHelper {

  private leaflet: any = null;
  private map: any = null;
  private geoJsonLayer: any = null;
  private tileLayer: any = null;
  private marker: any = null;
  private highlightLayer: any = null;

  async initMap(elementId: string, center: any, zoom: number, minZoom : number, maxZoom : number): Promise<void> {
    const L = await import('leaflet');
    this.leaflet = L.default ?? L;

    this.map = this.leaflet.map(elementId).setView(center, zoom);

    this.map.setMinZoom(minZoom);
    this.map.setMaxZoom(maxZoom);
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
      this.onTileSelected(option.id!);
    }
  }

  onTileSelected(mapId:number):void{
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
}
