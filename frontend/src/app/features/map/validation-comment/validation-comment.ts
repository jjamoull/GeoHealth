import {Component, Input} from '@angular/core';
import {MatExpansionPanel, MatExpansionPanelHeader} from '@angular/material/expansion';

@Component({
  selector: 'app-validation-comment',
  imports: [
    MatExpansionPanelHeader,
    MatExpansionPanel
  ],
  templateUrl: './validation-comment.html',
  styleUrl: './validation-comment.css',
})
export class ValidationComment {
  @Input() username: string="";
  @Input() department: string="";
  @Input() agreementLevel:number|null=0;
  @Input() perceivedRisk:string|null="";
  @Input() certaintyLevel: number|null=0;
  @Input() comment: string|null="";
}
