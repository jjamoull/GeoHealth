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

export function getColorFromCmbnd(value : number, isDry: boolean){
  // allow to manage the value between 0 and 1 if value is out of range
  const finalValue = Math.min(1, Math.max(0, value));

  if (isDry){
    const r = Math.round(255 - (finalValue * (255 - 139)));
    const g = Math.round(204 - (finalValue * 204));
    const b = Math.round(204 - (finalValue * 204));
    return `rgb(${r}, ${g}, ${b})`;
  } else {
    const r = Math.round(204 - (finalValue * 204));
    const g = Math.round(204 - (finalValue * 204));
    const b = Math.round(255 - (finalValue * (255 - 139)));
    return `rgb(${r}, ${g}, ${b})`;
  }

}
