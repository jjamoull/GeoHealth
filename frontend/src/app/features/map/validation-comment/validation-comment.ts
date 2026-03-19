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
  @Input() username: string="Jajmoulle";
  @Input() department: string="Mbfébé";
  @Input() agreementLevel:number|null=3;
  @Input() perceivedRisk:string|null="low";
  @Input() certaintyLevel: number|null=2;
  @Input() comment: string|null="commenLe lorem ipsum est, en imprimerie, une suite de mots sans signification utilisée à titre provisoire pour calibrer une mise en page, le texte définitif venant remplacer le faux-texte dès qu'il est prêt ou que la mise en page est achevée.commenLe lorem ipsum est, en imprimerie, une suite de mots sans signcommenLe lorem ipsum est, en imprimerie, une suite de mots sans signcommenLe lorem ipsum est, en imprimerie, une suite de mots sans signcommenLe lorem ipsum est, en imprimerie, une suite de mots sans signcommenLe lorem ipsum est, en imprimerie, une suite de mots sans signcommenLe lorem ipsum est, en imprimerie, une suite de mots sans signcommenLe lorem ipsum est, en imprimerie, une suite de mots sans signcommenLe lorem ipsum est, en imprimerie, une suite de mots sans signcommenLe lorem ipsum est, en imprimerie, une suite de mots sans signcommenLe lorem ipsum est, en imprimerie, une suite de mots sans signcommenLe lorem ipsum est, en imprimerie, une suite de mots sans signcommenLe lorem ipsum est, en imprimerie, une suite de mots sans signcommenLe lorem ipsum est, en imprimerie, une suite de mots sans signcommenLe lorem ipsum est, en imprimerie, une suite de mots sans sign";

}
