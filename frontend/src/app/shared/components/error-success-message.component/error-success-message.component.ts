import {Component, Input, signal} from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-error-success-message',
  imports: [CommonModule],
  templateUrl : './error-success-message.component.html',
  styleUrl: './error-success-message.component.css',
})
export class ErrorSuccessMessageComponent {
  @Input() errorMessages = signal('');
  @Input() showErrors = signal(false);

}
