import {Component, Inject, OnInit} from '@angular/core';
import {MatDialog,MatDialogRef, MatDialogModule, MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector: 'app-pop-up',
  imports: [],
  templateUrl: './pop-up.html',
  styleUrl: './pop-up.css',
})
export class PopUp{
  constructor(private dialog: MatDialogRef <PopUp>) {}

  /**
   * Allow the user to close the pop-up
   * */
  closePopUp(): void {
    this.dialog.close();
  }

}
