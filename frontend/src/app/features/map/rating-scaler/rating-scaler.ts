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

  onSelect(option: string): void {
    if (this.value === option) {
      this.value = '';
      this.valueChange.emit('');
    } else {
      this.value = option;
      this.valueChange.emit(option);
    }
  }
}
