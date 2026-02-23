import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-button',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './button.component.html',
  styleUrl: './button.component.css',

})
export class ButtonComponent {
  @Input() text: string = '';
  @Input() variant: 'success' | 'danger' | 'primary' = 'success';
  @Input() disabled:boolean= false;
  @Output() clicked = new EventEmitter<void>();


  handleClick() {
    this.clicked.emit();
  }
}

