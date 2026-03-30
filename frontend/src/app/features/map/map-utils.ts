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
