import {Component, Inject, Input, OnInit} from '@angular/core';
import {MatDialog,MatDialogRef, MatDialogModule, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {finalize, Subscription} from 'rxjs';
import {HttpClient, HttpEvent, HttpEventType} from '@angular/common/http';
import {MapService} from '../../core/service/MapService/mapService';
import {PopupService} from '../../core/service/PopupService/popupService';

@Component({
  selector: 'app-pop-up',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './pop-up.component.html',
  styleUrl: './pop-up.component.css',
})
export class PopUpComponent implements OnInit{
  constructor(private dialog: MatDialogRef <PopUpComponent>,
              private http: HttpClient,
              private mapService: MapService,
              private popupService: PopupService,
              @Inject(MAT_DIALOG_DATA) public data: any) {}

  //************ Constants ************
  addMap:string ="addMap";
  addRiskFactor : string = "addRiskFactor"
  //***********************************

  //******** Global variables *********
  problemWithUploading:boolean = false;
  typeOfPopUp: string = "addRiskFactor";
  formGroup!: FormGroup;
  selectedFile: File | null = null;
  isUploading = false;
  @Input()
  requiredFileType:string = '';
  //***********************************



  /**
   * Init the form to add the new map on the list of map
   */
  ngOnInit():void {
    this.problemWithUploading = false;

    this.typeOfPopUp = this.data.typeOfPopUp;
    this.formGroup = new FormGroup({
      title: new FormControl('',[Validators.required, Validators.minLength(1)]),
      description: new FormControl('', [Validators.required, Validators.minLength(1)]),
      file: new FormControl(null, [Validators.required])
    });
  }



  /**
   * Handles the file upload component contained in a pop-up
   * */
  onFileSelected(event:Event) {
    this.problemWithUploading = false;

    // This constant recieve the set of data from the event
    const input = event.target as HTMLInputElement;

    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
      this.isUploading = false;
    }
  }

  /**
   * Methods that contain all situation of sending data to backend
   * */
  private sendData(formData: FormData){
    console.log("typeOfPopOp =", this.typeOfPopUp);

    if (this.typeOfPopUp == this.addMap){
      this.mapService.uploadNewMap(formData).subscribe(
        {
          next:()=>{
            this.isUploading = false;
            //this.closePopUp()
          }, error:(error)=>{
            console.error(error);
            this.isUploading = false;
          }
        }
      );
    } else if(this.typeOfPopUp == this.addRiskFactor) {
      this.popupService.uploadNewRiskFactor(formData).subscribe(
        {
          next:()=>{
            this.isUploading = false;
            this.dialog.close();

            //this.closePopUp()
          }, error:(error)=>{
            this.problemWithUploading = true;
            console.error(error);
            this.isUploading = false;
          }
        }
      );
    } else {
      throw new Error("Impossible to send data to add your new feature ! \n Issue with the type of feature you want to add");
    }
  }

  /**
   * Allow the user to close the pop-up
   * */
  closePopUp(): void {
    if (!this.selectedFile) {
      return;
    }

    //add all values from the form in FormData to send in DB
    const formData = new FormData();
    formData.append("title", this.formGroup.value.title);
    formData.append("description", this.formGroup.value.description);
    formData.append("tifFile", this.selectedFile);

    this.sendData(formData);
  }


  /**
   * Allow to show at the user if his input is correct or not before to send it to the backend
   * Check precondition of the type of transformation
   * */
  isFieldValid(name: string){
    const formControl = this.formGroup.get(name);
    return formControl?.invalid && formControl?.dirty;
  }


  typeOfPopopIsValid(name:string){
    if (this.typeOfPopUp == name){
      return true;
    }else {
      return false;
    }
  }





}
