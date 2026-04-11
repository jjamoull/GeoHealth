import { TileMeanAndXYdto } from '../../shared/models/MapModel/RasterMapModel/TileMeanAndXYdto';

/**
 * Fetches the mean pixel value of a specific block in a tile
 *
 * @param mapId - the id of the raster map
 * @param z - the current zoom level on the map
 * @param lat - the latitude of the clicked point
 * @param lng - the longitude of the clicked point
 * @returns the mean value and coordinates of the block, or null if the request failed
 */
export async function getTileMean(
  mapId: number,
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

/**
 * Converts tile and block coordinates into lat/lng polygon bounds
 * used to draw the highlight rectangle on the map
 *
 * @param x - the x coordinate of the tile in OSM tile space
 * @param y - the y coordinate of the tile in OSM tile space
 * @param z - the current zoom level
 * @param blockX - the x coordinate of the block within the tile (0 to TILE_SIZE/BLOCK_SIZE)
 * @param blockY - the y coordinate of the block within the tile (0 to TILE_SIZE/BLOCK_SIZE)
 * @returns array of [lat, lng] pairs representing the four corners of the block
 */
export function tileToPolygon(x: number, y: number, z: number, blockX : number, blockY : number) {
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
