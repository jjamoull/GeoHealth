import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-tooltip-description',
  imports: [CommonModule],
  templateUrl: './tooltip-description.html',
  styleUrl: './tooltip-description.css',
})
export class TooltipDescriptionComponent {
  @Input() description: string = '';
}
