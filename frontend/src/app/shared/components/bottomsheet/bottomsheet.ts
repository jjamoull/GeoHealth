import {Component, Inject} from '@angular/core';
import {Checkbox} from '../checkbox/checkbox';
import {MAT_BOTTOM_SHEET_DATA} from '@angular/material/bottom-sheet';
import {TranslocoPipe} from '@jsverse/transloco';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

@Component({
  selector: 'app-bottomsheet',
  imports: [
    TranslocoPipe,
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './bottomsheet.html',
  styleUrl: './bottomsheet.css',
})

export class Bottomsheet {
  constructor(@Inject(MAT_BOTTOM_SHEET_DATA) public data: any) {}
}
