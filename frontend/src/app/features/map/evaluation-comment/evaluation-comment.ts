import {Component, Input} from '@angular/core';
import {MatExpansionPanel, MatExpansionPanelHeader} from '@angular/material/expansion';

@Component({
  selector: 'app-evaluation-comment',
  imports: [
    MatExpansionPanelHeader,
    MatExpansionPanel
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
}
