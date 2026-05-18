import {Component, OnInit, ChangeDetectorRef} from '@angular/core';
import {MapUploadModalComponent} from './map-upload-modal/map-upload-modal';
import {MatDialog, MatDialogModule} from '@angular/material/dialog';
import {Router} from '@angular/router';
import {Checkbox} from '../../../../shared/components/checkbox/checkbox';
import {Bottomsheet} from '../../../../shared/components/bottomsheet/bottomsheet';
import {FinalMapService} from '../../../../core/service/MapService/FinalMapService/finalMapService';
import {FinalMapListDto} from '../../../../shared/models/MapModel/FinalMapModel/FinalMapListDto';
import {UsersServices} from '../../../../core/service/UserService/users-services';
import {MapPreviewComponent} from '../../../map-preview-component/map-preview-component';
import { FormsModule } from '@angular/forms';
import { AdminFinalMapService } from '../../../../core/service/AdminService/AdminMapService/AdminFinalMapService'
import {TranslocoPipe} from '@jsverse/transloco';
import {MatBottomSheet} from '@angular/material/bottom-sheet';
import {isPlatformBrowser, NgOptimizedImage} from '@angular/common';
import {TruncatePipe} from '../../../../shared/pipes/truncate.pipe';
import { PLATFORM_ID, Inject } from '@angular/core';

@Component({
  selector: 'app-navigation',
  imports: [MatDialogModule, MapPreviewComponent, FormsModule, TranslocoPipe, NgOptimizedImage, TruncatePipe],
  templateUrl: './navigation-page.component.html',
  styleUrl: './navigation-page.component.css',
})
export class NavigationPageComponent implements OnInit{

  constructor(
    private dialog: MatDialog,
    private router: Router,
    private finalMapService: FinalMapService,
    private cdr: ChangeDetectorRef,
    private usersServices: UsersServices,
    private adminFinalService: AdminFinalMapService,
    private BottomSheet: MatBottomSheet
  ){}

  isAdmin:boolean =false;

  searchText: string = '';
  filteredMaps: FinalMapListDto[] = [];


  isSearching: boolean = false;
  noResults: boolean = false;

  listOfAllMaps:FinalMapListDto[] = [];
  listOfAllRecentMaps:FinalMapListDto[] = [];

  finalRisksOpen = true;
  riskFactorsOpen = false;

  openMenu(){
    this.BottomSheet.open(Bottomsheet, {
      data: {
        openPopUp: (type: string) => this.openPopUp(type),
        isAdmin: this.isAdmin
      }
    });
  }

  toggleDropdown(section: string) {
    switch(section) {
      case 'finalRisks':
        this.finalRisksOpen = !this.finalRisksOpen;
        break;

      case 'riskFactors':
        this.riskFactorsOpen = !this.riskFactorsOpen;
        break;
    }
  }

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
        this.filteredMaps = maps;
        this.cdr.detectChanges();
      },
      error: (err: any) => {
        console.error('Error fetching maps', err);
      }
    });
  }

  /* Ignore caps and accents */
  private normalize(text: string): string {
    return text
      ?.toLowerCase()
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '');
  }

  /* Fuzzy match with Levenshtein algorithm */
  private fuzzyMatch(search: string, target: string): boolean {
    const words = target.split(' ');
    return words.some(word => this.levenshtein(search, word) <= 2);
  }

  /* Levenshtein algorithm to include results that are close but not exactly similar to input*/
  private levenshtein(a: string, b: string): number {

    const matrix = [];

    for (let i = 0; i <= b.length; i++) {
      matrix[i] = [i];
    }

    for (let j = 0; j <= a.length; j++) {
      matrix[0][j] = j;
    }

    for (let i = 1; i <= b.length; i++) {
      for (let j = 1; j <= a.length; j++) {
        matrix[i][j] =
          b[i - 1] === a[j - 1]
            ? matrix[i - 1][j - 1]
            : Math.min(
              matrix[i - 1][j - 1] + 1,
              matrix[i][j - 1] + 1,
              matrix[i - 1][j] + 1
            );
      }
    }

    return matrix[b.length][a.length];
  }


  /**
   * Searches the list of maps based on the current `searchText` value.
   *
   * @param searchText The user input used to filter the list of maps.
   * @return void
   *
   * Updates:
   * - `isSearching` to indicate whether a search is in progress.
   * - `filteredMaps` with the maps whose titles match the search term.
   * - `noResults` to true when no map matches the search term.
   */
  searchMaps(): void {
    const search = this.normalize(this.searchText);

    if (!search) {
      this.isSearching = false;
      this.noResults = false;
      this.filteredMaps = [];
      this.applyFilters();
      return;
    }

    this.isSearching = true;

    let result = this.listOfAllMaps.filter(map =>
      this.normalize(map.title).includes(search)
    );

    // applique aussi le filtre saison pendant la recherche
    if (this.dryChecked || this.wetChecked) {
      result = result.filter(map => {
        const tags = map.tags?.map((t: string) => t.toLowerCase()) ?? [];
        return (
          (this.dryChecked && tags.includes('dry')) ||
          (this.wetChecked && tags.includes('wet'))
        );
      });
    }

    this.filteredMaps = result;
    this.noResults = this.filteredMaps.length === 0;
  }


  clearSearch(): void {
    this.searchText = '';
    this.isSearching = false;
    this.noResults = false;
    this.filteredMaps = [];
  }

  /**
   * Allow the user to open the pop-up on click event
   * */
  openPopUp(paramTypeOfPopUp:string): void {
    const dialog = this.dialog.open(MapUploadModalComponent, {
      data: { typeOfPopUp: paramTypeOfPopUp },
      disableClose: false
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
   * Deletes the map once called
   */
  deleteFinalMap(mapId: number): void {
    if (!confirm('Are you sure you want to delete this map?')) return;

    this.adminFinalService.deleteFinalMap(mapId).subscribe({
      next: () => {
        this.listOfAllMaps = this.listOfAllMaps.filter(m => m.id !== mapId);
        window.location.reload();
      },
      error: (err: any) => {
        console.error('Failed to delete map', err);
      }
    });
  }

  diseaseDropdownOpen = false;
  diseases = ['Ebola', 'RVF (Rift Valley Fever)'];
  selectedDiseases: string[] = [...this.diseases];

  toggleDiseaseDropdown() {
    this.diseaseDropdownOpen = !this.diseaseDropdownOpen;
  }

  toggleDisease(disease: string) {
    const index = this.selectedDiseases.indexOf(disease);
    index === -1
      ? this.selectedDiseases.push(disease)
      : this.selectedDiseases.splice(index, 1);
  }


  dryChecked = false;
  wetChecked = false;

  toggleSeason(season: 'dry' | 'wet') {
    if (season === 'dry') this.dryChecked = !this.dryChecked;
    if (season === 'wet') this.wetChecked = !this.wetChecked;
    this.applyFilters();
  }

  private applyFilters() {
    let result = this.listOfAllMaps;

    if (this.dryChecked || this.wetChecked) {
      result = result.filter(map => {
        if (!map.tags) return false;
        const tags = map.tags.map((t: string) => t.toLowerCase());
        return (
          (this.dryChecked && tags.some(t => t.includes('dry'))) ||
          (this.wetChecked && tags.some(t => t.includes('wet')))
        );
      });
    }

    this.filteredMaps = result;
    this.isSearching = this.dryChecked || this.wetChecked;
    this.noResults = this.filteredMaps.length === 0;
    this.cdr.detectChanges();
  }
}

