import { Component } from '@angular/core';
import {PopUp} from '../pop-up/pop-up';
import {MatDialog, MatDialogModule, MatDialogRef} from '@angular/material/dialog';
import {ListOfAllMaps} from './ListOfAllMaps';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-navigation',
  imports: [MatDialogModule],
  templateUrl: './navigation.html',
  styleUrl: './navigation.css',
})
export class Navigation {

  constructor(private dialog: MatDialog,
              private router: Router) {}

  /*
  * Set of all global variables of this file
  * */
  listOfAllMaps:ListOfAllMaps[] = [{
    titre: 'test2', description:'description du test2', id: 0
  }, {
    titre: 'test2', description:'description du test2', id: 1
  }, {
    titre: 'test2', description:'description du test2', id: 2
  }, {
    titre: 'test2', description:'description du test2', id: 3
  }, {
    titre: 'test2', description:'description du test2', id: 4
  }, {
    titre: 'test2', description:'description du test2', id: 5
  }, {
    titre: 'test2', description:'description du test2', id: 6
  }, {
    titre: 'test2', description:'description du test2', id: 7
  }, {
    titre: 'test2', description:'description du test2', id: 8
  }, {
    titre: 'test2', description:'description du test2', id: 9
  }, {
    titre: 'test2', description:'description du test2', id: 10
  }, {
    titre: 'test2', description:'description du test2', id: 11
  }, {
    titre: 'test2', description:'description du test2', id: 12
  }, {
    titre: 'test2', description:'description du test2', id: 13
  }, {
    titre: 'test2', description:'description du test2', id: 14
  }, {
    titre: 'test2', description:'description du test2', id: 15
  }]

  /*
* Set of all global variables of this file
* */
  listOfAllRecentMaps:ListOfAllMaps[] = [{
    titre: 'testRecent2', description:'description du testRecent2', id: 16
  }, {
    titre: 'testRecent2', description:'description du testRecent2', id: 17
  }, {
    titre: 'testRecent2', description:'description du testRecent2', id: 18
  }]

  //----------------------------------------------------

  /**
   * Allow the user to open the pop-up on click event
   * */
  openPopUp(): void {
    this.dialog.open(PopUp);
  }

  /**
   * Go to the page with the id of the map
   * */
  goToMap(id:number){
    this.router.navigate(['map/'+id],{
      queryParams: {id:id},
      queryParamsHandling: 'merge'
    });
  }


}
