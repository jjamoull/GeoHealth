import { LegendItem } from './map-types';

export const RISK_LEVELS: LegendItem[] = [
  { label: 'Low',    color: '#2ecc71' },
  { label: 'Medium', color: '#f39c12' },
  { label: 'High',   color: '#e74c3c' },
];

export function getRiskColor(riskClass: string): string {
  const level = RISK_LEVELS.find(l => l.label === riskClass);
  return level ? level.color : '#aaaaaa';
}

/**
 * Display layers on a color adapted of the value of the boolean isDry
 *
 * @param value - value of the average risk index that serve for a range of value concerning the color
 * @param isDry - true if the tag is dry, false otherwise
 * @return a String with the rgb value for the color to display
 * */
export function getColorFromCmbnd(value: number, isDry: boolean): string {
  const MIN = 0.20;
  const MAX = 0.80;

  // allow to manage the value between MIN and MAX if value is out of range
  const normalized = Math.min(1, Math.max(0, (value - MIN) / (MAX - MIN)));
  const curved = Math.pow(normalized, 1.5);

  if (isDry) {
    const r = Math.round(255 - (curved * (255 - 139)));
    const g = Math.round(230 - (curved * 230));
    const b = Math.round(230 - (curved * 230));
    return `rgb(${r}, ${g}, ${b})`;
  } else {
    const r = Math.round(230 - (curved * 230));
    const g = Math.round(230 - (curved * 230));
    const b = Math.round(255 - (curved * (255 - 139)));
    return `rgb(${r}, ${g}, ${b})`;
  }
}
