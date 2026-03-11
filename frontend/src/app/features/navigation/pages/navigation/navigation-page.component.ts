import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { isPlatformBrowser} from '@angular/common';
import {PopUpComponent} from '../../../pop-up/pop-up.component';
import {MatDialog, MatDialogModule, MatDialogRef} from '@angular/material/dialog';
import {ActivatedRoute, Router} from '@angular/router';
import {ButtonComponent} from "../../../../shared/components/button.component/button.component";
import {Checkbox} from '../../../../shared/components/checkbox/checkbox';
import {FinalMapService} from '../../../../core/service/MapService/FinalMapService/finalMapService';
import {FinalMapListDto} from '../../../../shared/models/MapModel/FinalMapModel/FinalMapListDto';

@Component({
  selector: 'app-navigation',
  imports: [MatDialogModule, ButtonComponent, Checkbox],
  templateUrl: './navigation-page.component.html',
  styleUrl: './navigation-page.component.css',
})
export class NavigationPageComponent implements OnInit{

  constructor(
    private dialog: MatDialog,
    private router: Router,
    private finalMapService: FinalMapService,
    private cdr: ChangeDetectorRef
    ){}

  listOfAllMaps:FinalMapListDto[] = [];
  listOfAllRecentMaps:FinalMapListDto[] = [];

  ngOnInit() {
      this.getAllMaps();
    }

  /**
   * add all the maps into ListOfAllMaps
   */
  private getAllMaps() {
    this.finalMapService.getAllMaps().subscribe({
      next: (maps: FinalMapListDto[]) => {
        this.listOfAllMaps = maps;
        this.cdr.detectChanges();
      },
      error: (err: any) => {
        console.error('Error fetching maps', err);
      }
    });
  }

  /**
   * Allow the user to open the pop-up on click event
   * */
  openPopUp(paramTypeOfPopUp:string): void {
    this.dialog.open(PopUpComponent, {
      data:{
        typeOfPopUp: paramTypeOfPopUp
      }
    });
  }


  /**
   * Go to the page with the id of the map
   */
  goToMap(id: number) {
    this.router.navigate(['/maps', id]);
  }
}
