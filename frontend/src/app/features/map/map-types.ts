export type MapKind = 'divisions' | 'tile';

export interface MapOption {
  id: number | null;
  kind: MapKind;
  title: string;
}

export interface LegendItem {
  label: string;
  color: string;
}
