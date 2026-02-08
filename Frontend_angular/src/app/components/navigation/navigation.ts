import { Component } from '@angular/core';
import {PopUp} from '../pop-up/pop-up';
import {MatDialog, MatDialogModule, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-navigation',
  imports: [MatDialogModule],
  templateUrl: './navigation.html',
  styleUrl: './navigation.css',
})
export class Navigation {

  constructor(private dialog: MatDialog) {}

  /**
   * Allow the use to open the pop-up
   * */
  openPopUp(): void {
    this.dialog.open(PopUp);
  }
}
