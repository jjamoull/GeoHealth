import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CdkDrag, CdkDragHandle } from '@angular/cdk/drag-drop';
import { ButtonComponent } from '../../../shared/components/button.component/button.component';
import { SaveValidationFormDto } from '../../../shared/models/ValidationFormModel/SaveValidationFormDto';
import { ValidationFormService } from '../../../core/service/ValidationFormService/validationFormService';
import { RatingScalerComponent } from '../rating-scaler/rating-scaler';

@Component({
  selector: 'app-evaluation-modal',
  standalone: true,
  imports: [CommonModule, FormsModule, CdkDrag, CdkDragHandle, ButtonComponent, RatingScalerComponent],
  templateUrl: './evaluation-modal.html',
  styleUrl: './evaluation-modal.css',
})
export class EvaluationModalComponent {
  @Input() department: any = null;
  @Output() close = new EventEmitter<void>();

  agreementLevel: number | null = null;
  certaintyLevel: number | null = null;
  perceivedRisk: string | null = null;
  comment: string | null = null;

  constructor(private validationFormService: ValidationFormService) {}

  onClose(): void {
    this.close.emit();
  }

  onSave(isPublic: boolean): void {
    const saveValidationFormDto: SaveValidationFormDto = {
      department: this.department?.NAME_2,
      agreementLevel: this.agreementLevel,
      certaintyLevel: this.certaintyLevel,
      perceivedRisk: this.perceivedRisk,
      comment: this.comment,
      isPublic: isPublic
    };

    this.validationFormService.saveForm(saveValidationFormDto).subscribe({
      next: () => {
        console.log('Form saved successfully');
        this.close.emit();
      },
      error: (err) => console.error('Error saving form:', err)
    });
  }
}
