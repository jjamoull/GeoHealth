import {Component, Inject, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {AdminRasterMapService} from '../../../core/service/AdminService/AdminMapService/AdminRiskFactorMapService';

import {InputboxComponents} from '../../../shared/components/inputbox.components/inputbox.components';


@Component({
  selector: 'app-final-raster-map',
  imports: [
    FormsModule,
    InputboxComponents,
    ReactiveFormsModule
  ],
  templateUrl: './final-raster-map.html',
  styleUrl: './final-raster-map.css',
})
export class FinalRasterMap implements OnInit{
  constructor(private dialog: MatDialogRef <FinalRasterMap>,
              private adminRasterMapService: AdminRasterMapService,
              @Inject(MAT_DIALOG_DATA) public data: any) {}


  //******** Global variables *********
  problemWithUploading:boolean = false;
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
    this.adminRasterMapService.uploadRasterMap(formData).subscribe(
      {
        next:()=>{
          this.isUploading = false;
          console.log("The new final raster map is sent to backend");
          this.dialog.close();

        }, error:(error)=>{
          this.problemWithUploading = true;
          console.error(error);
          this.isUploading = false;
        }
      }
    );
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
    formData.append("typeOfRaster", "final_raster_map");
    formData.append("tifFile", this.selectedFile);


      this.sendData(formData);
    console.log("the final raster map '" + formData.get("title") + "' is sent")
  }


  /**
   * Allow to show at the user if his input is correct or not before to send it to the backend
   * Check precondition of the type of transformation
   * */
  isFieldValid(name: string){
    const formControl = this.formGroup.get(name);
    return formControl?.invalid && formControl?.dirty;
  }
}
