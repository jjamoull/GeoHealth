import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { isPlatformBrowser} from '@angular/common';
import {PopUpComponent} from '../../../pop-up/pop-up.component';
import {MatDialog, MatDialogModule, MatDialogRef} from '@angular/material/dialog';
import {ActivatedRoute, Router} from '@angular/router';
import {ButtonComponent} from "../../../../shared/components/button.component/button.component";
import {Checkbox} from '../../../../shared/components/checkbox/checkbox';
import {MapService} from '../../../../core/service/MapService/mapService';
import {MapListDto} from '../../../../shared/models/MapModel/MapListDto';

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
    private mapService: MapService,
    private cdr: ChangeDetectorRef
    ){}

  listOfAllMaps:MapListDto[] = [];
  listOfAllRecentMaps:MapListDto[] = [];

  ngOnInit() {
      this.getAllMaps();
    }

  /**
   * add all the maps into ListOfAllMaps
   */
  private getAllMaps() {
    this.mapService.getAllMaps().subscribe({
      next: (maps: MapListDto[]) => {
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
