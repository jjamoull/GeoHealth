import { getRiskColor } from './map-utils';
import { MapOption } from './map-types';
import { TileMeanAndXYdto } from '../../shared/models/MapModel/RasterMapModel/TileMeanAndXYdto';
import { getTileMean, tileToPolygon } from './tile-utils';

export class MapLayerHelper {

  // Leaflet library instance
  private leaflet: any = null;
  // the main Leaflet map instance
  private map: any = null;
  // the GeoJSON layer displaying the divisions
  private geoJsonLayer: any = null;
  // the raster tile layer displayed
  private tileLayer: any = null;
  // the blue dot marker placed on division click
  private marker: any = null;
  // the red rectangle highlighting the clicked tile block
  private highlightLayer: any = null;
  // all annotation on the map
  private geoManLayer: any;

  private inspectModeActive: boolean = false;

  /**
   * Initializes the Leaflet map on the given HTML element
   * and adds the OpenStreetMap background tiles
   *
   * @param elementId - the id of the HTML element to render the map in
   * @param center - the initial center coordinates [lat, lng]
   * @param zoom - the initial zoom level
   * @param minZoom - the minimum allowed zoom level
   * @param maxZoom - the maximum allowed zoom level
   */
  async initMap(elementId: string, center: any, zoom: number, minZoom : number, maxZoom : number, enableGeoman: boolean): Promise<void> {
    console.log("In init map");
    const L = await import('leaflet');
    if (enableGeoman) {
      await import('@geoman-io/leaflet-geoman-free');
    }

    this.leaflet = L.default ?? L;

    this.map = this.leaflet.map(elementId).setView(center, zoom);

    this.map.setMinZoom(minZoom);
    this.map.setMaxZoom(maxZoom);
    this.map.createPane('markerPane');
    this.map.getPane('markerPane').style.zIndex = 400;

    this.leaflet.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap'
    }).addTo(this.map);

    this.geoManLayer = this.leaflet.featureGroup().addTo(this.map);

    if (enableGeoman) {
      (this.map as any).pm.addControls({
        position: 'topleft',
        drawCircleMarker: false,
        rotateMode: false,
      });
    }

    (this.map as any).pm.setGlobalOptions({ layerGroup: this.geoManLayer });
  }

  /**
   * Make transparent annotations on the layer on OSM
   *    if active is True,
   *    else make usable annotations otherwise
   *
   * @param active : boolean variable that influence annotations display
   *    True : transparent annotations (of Geoman library, not geojson from shapefile)
   *    False : usable annotations
   * */
  toggleInspectMode(active: boolean): void {
    this.inspectModeActive = active;
    if (!this.geoManLayer) {
      return;
    }

    this.geoManLayer.eachLayer((layer: any) => {
      let element = null;

      // find markers
      if (layer.getElement) {
        element = layer.getElement();
      }

      // find polygons and lines
      if (!element && layer._path) {
        element = layer._path;
      }

      // find canvas
      if (!element && layer._renderer) {
        element = layer._renderer._container;
      }

      if (element){
        if (active){
          element.style.pointerEvents = 'none';
        } else {
          element.style.pointerEvents = 'auto';
        }
      }
    });
  }

  /**
   * Get annotations and transform it into string to allow the data to be sent to backend
   *
   * @return :
   *    GeoJSON data (String) : if the geoman data can be translated and ready to send
   *    null : otherwise
   * */
  getGeomanGeojson(): String | null {
    if (this.geoManLayer == null){
      return null;
    }

    try {
      const geomanInGeojson = this.geoManLayer.toGeoJSON();

      if (geomanInGeojson.feature.length === 0){
        return null;
      }else {
        return JSON.stringify(geomanInGeojson);
      }
    } catch (e) {
        console.log("Issue during transformation of annotations")
        return null;
    }
  }

  /**
   * Take geojson data in string format into GeoJSON applicable on the
   * layer and add it on the map
   *
   * @param geoJsonString : GeoJSON data in String format
   * */
  loadAnnotationsFromGeoJson(geoJsonString: string): void {
    if (!this.leaflet || !this.geoManLayer) {
      return;
    }

    this.geoManLayer.clearLayers();
    const geoJsonData = JSON.parse(geoJsonString);

    this.leaflet.geoJSON(geoJsonData).eachLayer((layer: any) => {
      this.geoManLayer.addLayer(layer);
    });
  }


  /**
   * Draws the GeoJSON divisions layer on the map
   * and colors each division based on its risk category
   *
   * @param geoJsonString - the GeoJSON string representing the divisions
   * @param onDivisionClick - callback fired when a division is clicked
   */
  applyDivisionsLayer(geoJsonString: string, onDivisionClick: (event: any) => void): void {
    const geoJson = JSON.parse(geoJsonString);

    this.geoJsonLayer = this.leaflet.geoJSON(geoJson, {
      style: (feature: any) => ({
        color: '#414241',
        weight: 1,
        fillColor: (getRiskColor(feature?.properties?.rsk_cls)),
        fillOpacity: 0.5,
      }),
      onEachFeature: (feature: any, layer: any) => {
        layer.options.pmIgnore = true;

        layer.on('mouseover', () => layer.setStyle({ weight: 2 }));
        layer.on('mouseout', () => layer.setStyle({ weight: 1 }));
        layer.on('click', (e: any) => {
          onDivisionClick({ properties: feature.properties, latlng: e.latlng });
        });
      }
    }).addTo(this.map);
    this.map.fitBounds(this.geoJsonLayer.getBounds());
  }

  /**
   * Switches the map display between divisions and a raster tile layer
   *
   * @param option - the selected map option containing the kind (divisions or tile) and optional id
   */
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

  /**
   * Listens for clicks on the map when a raster tile layer is active
   * fetches the mean value of the clicked block and highlights it in red
   *
   * @param mapId - the id of the raster map currently displayed
   */
  onTileSelected(mapId:number):void{
      this.map.on('click', async (e:any)=>{
        console.log("\n [onTileSelected] : " + e.latlng)
        const coordinates : any = e.latlng
        const z : number = this.map.getZoom();


        console.log(mapId);
        console.log(coordinates.lat, coordinates.lng);
        const blockData : TileMeanAndXYdto | null = await getTileMean(mapId, z, coordinates.lat, coordinates.lng);


        if (blockData){
          console.log(blockData.mean);
          const bounds = tileToPolygon(blockData.tileX, blockData.tileY, z, blockData.blockX, blockData.blockY);

          if (this.highlightLayer) {
            this.map.removeLayer(this.highlightLayer);
          }

          this.highlightLayer = this.leaflet.polygon(bounds, {
            color: 'red',
            weight: 2,
            fillOpacity: 0.1
          }).addTo(this.map);
      }
      });
    }

  /**
   * Places a blue circle marker at the given coordinates
   *
   * @param latlng - the coordinates where the marker should be placed
   */
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

  /**
   * Removes the current marker from the map if one exists
   */
  clearMarker(): void {
    if (this.marker) {
      this.marker.remove();
      this.marker = null;
    }
  }

  /**
   * Removes the current raster tile layer from the map if one exists
   */
  clearTileLayer(): void {
    if (this.tileLayer) {
      this.tileLayer.remove();
      this.tileLayer = null;
    }
  }

  getAnnotations(): any {
    console.log(this.geoManLayer.toGeoJSON());
  }
}
