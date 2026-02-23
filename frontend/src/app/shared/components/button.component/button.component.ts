import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-button',
  imports: [],
  templateUrl: './button.component.html',
  styleUrl: './button.component.css',

})
export class ButtonComponent {
  @Input() text: string = '';
  @Input() variant: 'success' | 'danger' | 'primary' = 'success';
  @Output() clicked = new EventEmitter<void>();

  handleClick() {
    this.clicked.emit();
  }
}
