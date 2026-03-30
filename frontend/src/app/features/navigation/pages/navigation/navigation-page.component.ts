import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import {PopUpComponent} from '../../../pop-up/pop-up.component';
import {MatDialog, MatDialogModule} from '@angular/material/dialog';
import {Router} from '@angular/router';
import {ButtonComponent} from "../../../../shared/components/button.component/button.component";
import {Checkbox} from '../../../../shared/components/checkbox/checkbox';
import {FinalMapService} from '../../../../core/service/MapService/FinalMapService/finalMapService';
import {FinalMapListDto} from '../../../../shared/models/MapModel/FinalMapModel/FinalMapListDto';
import {UsersServices} from '../../../../core/service/UserService/users-services';
import {MapComponent} from '../../../map/map.component';
import {CAMEROON_COORDINATES} from '../../../../features/map/map.constants';
import {CAMEROON_ZOOM} from '../../../../features/map/map.constants';

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
    private cdr: ChangeDetectorRef,
    private usersServices: UsersServices
    ){}

  isAdmin:boolean =false;

  listOfAllMaps:FinalMapListDto[] = [];
  listOfAllRecentMaps:FinalMapListDto[] = [];

  ngOnInit() {
    this.getAllMaps();

    this.usersServices.isAdmin().subscribe(
      bool=>{
        this.isAdmin=bool
        this.cdr.detectChanges();
      }
    );


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
    const dialog =this.dialog.open(PopUpComponent, {
      data:{
        typeOfPopUp: paramTypeOfPopUp
      }
    });

    dialog.afterClosed().subscribe(
      result=>{
        this.getAllMaps();
        this.cdr.detectChanges();}
    );

  }

  /**
   * Go to the page with the id of the map
   */
  goToMap(id: number) {
    this.router.navigate(['/maps', id]);
  }

  /**
   *
   * */
  getStaticMapUrl(): string {
    let lat = CAMEROON_COORDINATES[0];
    let lng = CAMEROON_COORDINATES[1];
    let zoom = CAMEROON_ZOOM;
    return `https://staticmap.openstreetmap.de/staticmap.php?center=${lat},${lng}&zoom=${zoom}&size=400x200&markers=${lat},${lng},red`;
  }

}
