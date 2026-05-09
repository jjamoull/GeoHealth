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
  @Input() mapType: string | undefined = '';
  @Input() season: string | undefined = '';

  get isDry(): boolean {
    return this.season === 'DRY';
  }

  get isEbola(): boolean {
    return this.mapType === 'EBOLA';
  }
}
