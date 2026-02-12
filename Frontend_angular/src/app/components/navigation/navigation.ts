import { Component } from '@angular/core';
import {PopUp} from '../pop-up/pop-up';
import {MatDialog, MatDialogModule, MatDialogRef} from '@angular/material/dialog';
import {ListOfAllMaps} from './ListOfAllMaps';

@Component({
  selector: 'app-navigation',
  imports: [MatDialogModule],
  templateUrl: './navigation.html',
  styleUrl: './navigation.css',
})
export class Navigation {

  constructor(private dialog: MatDialog) {}

  /*
  * Set of all global variables of this file
  * */
  listOfAllMaps:ListOfAllMaps[] = [{
    titre: 'test', description:'description du test', id: 0
  }, {
    titre: 'test2', description:'description du test2', id: 1
  }, {
    titre: 'test2', description:'description du test2', id: 1
  }, {
    titre: 'test2', description:'description du test2', id: 1
  }, {
    titre: 'test2', description:'description du test2', id: 1
  }, {
    titre: 'test2', description:'description du test2', id: 1
  }, {
    titre: 'test2', description:'description du test2', id: 1
  }, {
    titre: 'test2', description:'description du test2', id: 1
  }, {
    titre: 'test2', description:'description du test2', id: 1
  }, {
    titre: 'test2', description:'description du test2', id: 1
  }, {
    titre: 'test2', description:'description du test2', id: 1
  }, {
    titre: 'test2', description:'description du test2', id: 1
  }, {
    titre: 'test2', description:'description du test2', id: 1
  }, {
    titre: 'test2', description:'description du test2', id: 1
  }, {
    titre: 'test2', description:'description du test2', id: 1
  }, {
    titre: 'test2', description:'description du test2', id: 1
  }]

  /*
* Set of all global variables of this file
* */
  listOfAllRecentMaps:ListOfAllMaps[] = [{
    titre: 'testRecent2', description:'description du testRecent2', id: 1
  }, {
    titre: 'testRecent2', description:'description du testRecent2', id: 1
  }, {
    titre: 'testRecent2', description:'description du testRecent2', id: 1
  }]

  //----------------------------------------------------

  /**
   * Allow the use to open the pop-up
   * */
  openPopUp(): void {
    this.dialog.open(PopUp);
  }


}
