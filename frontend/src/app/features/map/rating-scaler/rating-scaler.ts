import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-rating-scaler',
  standalone: true,
  imports: [],
  templateUrl: './rating-scaler.html',
  styleUrl: './rating-scaler.css',
})
export class RatingScalerComponent {
  @Input() name: string = '';
  @Input() options: string[] = [];
  @Input() value: string = '';
  @Output() valueChange = new EventEmitter<string>();

  onSelect(value: string): void {
    this.valueChange.emit(value);
  }
}
