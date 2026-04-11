import {Component, Input} from '@angular/core';
import {MatExpansionPanel, MatExpansionPanelHeader} from '@angular/material/expansion';
import {AdminEvaluationFormService} from '../../../core/service/AdminService/AdminEvaluationFormService/AdminEvaluationFormService';
import {ButtonComponent} from '../../../shared/components/button.component/button.component';

@Component({
  selector: 'app-evaluation-comment',
  imports: [
    MatExpansionPanelHeader,
    MatExpansionPanel,
    ButtonComponent
  ],
  templateUrl: './evaluation-comment.html',
  styleUrl: './evaluation-comment.css',
})
export class EvaluationCommentComponent {
  @Input() username: string="";
  @Input() division: string="";
  @Input() agreementLevel:number|null=0;
  @Input() perceivedRisk:string|null="";
  @Input() certaintyLevel: number|null=0;
  @Input() comment: string|null="";
  @Input() firstName: string="";
  @Input() lastName: string="";
  @Input() formId: number=0;
  @Input() isAdmin: boolean=false;

  constructor(private adminEvaluationFormService: AdminEvaluationFormService) {}

  /**
    * Delete an evaluation form
    * Shows confirmation dialog before deletion
    */
  onDeleteEvaluation(): void {
    if(confirm('Are you sure you want to delete this form?')) {
      this.adminEvaluationFormService.deleteForm(this.formId).subscribe(
        (response) => {
          console.log(response.message);
          location.reload();
          },
        (error) => {
          console.log("Error deleting form:", error);
          }
        )
      }
    }
}
