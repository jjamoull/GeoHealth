export type MapKind = 'divisions' | 'raster';

export interface MapOption {
  id: number | null;
  kind: MapKind;
  title: string;
}

export interface LegendItem {
  label: string;
  color: string;
}
