import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule} from '@angular/common';
import { CdkDrag, CdkDragHandle } from '@angular/cdk/drag-drop';

@Component({
  selector: 'app-evaluation-modal',
  standalone: true,
  imports: [CommonModule, CdkDrag, CdkDragHandle],
  templateUrl: './evaluation-modal.html',
  styleUrl: './evaluation-modal.css',
})
export class EvaluationModalComponent {
  @Input() district:any = null;
  @Output() close = new EventEmitter<void>();

  onClose(): void {
      this.close.emit();
    }
}
