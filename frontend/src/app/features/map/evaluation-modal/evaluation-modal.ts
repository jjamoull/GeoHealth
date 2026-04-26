import { Component, Input, Output, EventEmitter, OnChanges} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CdkDrag, CdkDragHandle } from '@angular/cdk/drag-drop';
import { ButtonComponent } from '../../../shared/components/button.component/button.component';
import { SaveEvaluationFormDto } from '../../../shared/models/EvaluationFormModel/SaveEvaluationFormDto';
import { EvaluationFormService } from '../../../core/service/EvaluationFormService/EvaluationFormService';
import { RatingScalerComponent } from '../rating-scaler/rating-scaler';

import {ResponseEvaluationFormDto} from '../../../shared/models/EvaluationFormModel/ResponseEvaluationFormDto';
import { UpdateEvaluationFormDto } from '../../../shared/models/EvaluationFormModel/UpdateEvaluationFormDto';
import {TooltipDescriptionComponent } from '../../../shared/components/tooltip-description/tooltip-description';
import {TranslocoPipe} from "@jsverse/transloco";


@Component({
  selector: 'app-evaluation-modal',
  standalone: true,
    imports: [CommonModule, FormsModule, CdkDrag, CdkDragHandle, ButtonComponent, RatingScalerComponent, TooltipDescriptionComponent, TranslocoPipe],
  templateUrl: './evaluation-modal.html',
  styleUrl: './evaluation-modal.css',
})
export class EvaluationModalComponent implements OnChanges {
  @Input() division: any = null;
  @Input() mapId: number= -1;
  @Input() existingForm: ResponseEvaluationFormDto | null = null;
  @Output() close = new EventEmitter<void>();


  riskOptions = [
    { value: 'low', label: 'evalmodal.firstoption' },
    { value: 'medium', label: 'evalmodal.secondoption' },
    { value: 'high', label: 'evalmodal.thirdoption' }
  ];



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

  constructor(private evaluationFormService: EvaluationFormService) {}

  onClose(): void {
    this.close.emit();
  }

  onSave(isPublic: boolean): void {
    if (this.existingForm === null) {
      const saveEvaluationFormDto: SaveEvaluationFormDto = {
        division: this.division?.NAME_2,
        agreementLevel: this.agreementLevel,
        certaintyLevel: this.certaintyLevel,
        perceivedRisk: this.perceivedRisk,
        comment: this.comment,
        finalMapId: this.mapId,
        isPublic: isPublic
      };

      this.evaluationFormService.saveForm(saveEvaluationFormDto).subscribe({
        next: () => {
          console.log('Form saved successfully');
          this.close.emit();
        },
        error: (err) => console.error('Error saving form:', err)
      });
    } else {
      const updateDto : UpdateEvaluationFormDto = {
        id: this.existingForm!.id,
        agreementLevel: this.agreementLevel,
        certaintyLevel: this.certaintyLevel?.toString() || null,
        perceivedRisk: this.perceivedRisk,
        comment: this.comment,
        isPublic: isPublic
      };
      this.evaluationFormService.updateForm(updateDto).subscribe({
        next: () => {
          console.log('Form updated successfully');
          this.close.emit();
        },
        error: (err) => console.error('Error updating form:', err)
      });
    }
  }
}
