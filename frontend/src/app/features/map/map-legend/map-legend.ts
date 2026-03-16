import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'map-legend',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './map-legend.html',
  styleUrl: './map-legend.css',
})
export class MapLegendComponent {
  @Input() riskLevels: { label: string; color: string }[] = [];
}
