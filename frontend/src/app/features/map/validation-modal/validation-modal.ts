import { Component, Input, Output, EventEmitter, OnChanges} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CdkDrag, CdkDragHandle } from '@angular/cdk/drag-drop';
import { ButtonComponent } from '../../../shared/components/button.component/button.component';
import { SaveValidationFormDto } from '../../../shared/models/ValidationFormModel/SaveValidationFormDto';
import { ValidationFormService } from '../../../core/service/ValidationFormService/validationFormService';
import { RatingScalerComponent } from '../rating-scaler/rating-scaler';
import {ResponseValidationFormDto} from '../../../shared/models/ValidationFormModel/ResponseValidationFormDto';
import { UpdateValidationFormDto } from '../../../shared/models/ValidationFormModel/UpdateValidationFormDto';

@Component({
  selector: 'app-validation-modal',
  standalone: true,
  imports: [CommonModule, FormsModule, CdkDrag, CdkDragHandle, ButtonComponent, RatingScalerComponent],
  templateUrl: './validation-modal.html',
  styleUrl: './validation-modal.css',
})
export class ValidationModalComponent implements OnChanges {
  @Input() department: any = null;
  @Input() existingForm: ResponseValidationFormDto | null = null;
  @Output() close = new EventEmitter<void>();

  agreementLevel: number | null = null;
  certaintyLevel: number | null = null;
  perceivedRisk: string | null = null;
  comment: string | null = null;
  isPublic: boolean = false;

  ngOnChanges(): void {
    this.agreementLevel = this.existingForm?.agreementLevel || null;
    this.certaintyLevel = this.existingForm?.certaintyLevel || null;
    this.perceivedRisk = this.existingForm?.perceivedRisk || null;
    this.comment = this.existingForm?.comment || null;
    this.isPublic = this.existingForm?.isPublic || false;
  }

  constructor(private validationFormService: ValidationFormService) {}

  onClose(): void {
    this.close.emit();
  }

  onSave(isPublic: boolean): void {
    if (this.existingForm === null) {
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
    } else {
      const updateDto : UpdateValidationFormDto = {
        id: this.existingForm!.id,
        agreementLevel: this.agreementLevel,
        certaintyLevel: this.certaintyLevel?.toString() || null,
        perceivedRisk: this.perceivedRisk,
        comment: this.comment,
        isPublic: isPublic
      };
      this.validationFormService.updateForm(updateDto).subscribe({
        next: () => {
          console.log('Form updated successfully');
          this.close.emit();
        },
        error: (err) => console.error('Error updating form:', err)
      });
    }
  }
}
