
import {Component, Inject, Input, OnInit} from '@angular/core';
import {MatDialogRef, MatDialogModule, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { Subscription} from 'rxjs';
import {FinalMapService} from '../../../core/service/MapService/FinalMapService/finalMapService';

@Component({
  selector: 'app-final-map',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './final-map.html',
  standalone: true,
  styleUrl: './final-map.css'
})
export class FinalMap implements OnInit{
  constructor(private dialog: MatDialogRef <FinalMap>,
              private finalMapService: FinalMapService) {}

  formGroup!: FormGroup;
  selectedFile: File | null = null;
  isUploading = false;
  @Input()
  requiredFileType:string = '';
  uploadProgress =  0;
  uploadSub: Subscription | undefined;


  /**
   * Init the form to add the new map on the list of map
   */
  ngOnInit():void {
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

    // This constant recieve the set of data from the event
    const input = event.target as HTMLInputElement;

    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
      this.isUploading = false;

      //add all values from the form in FormData to send in DB
      const formData = new FormData();
      formData.append("title", this.formGroup.value.title);
      formData.append("description", this.formGroup.value.description);
      formData.append("zipFile", this.selectedFile);


      this.finalMapService.uploadNewMap(formData).subscribe(
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
    }
  }

  /**
   * Cancel upload, user-centered
   * */
  cancelUpload() {
    if (this.uploadSub){
      this.uploadSub.unsubscribe();}
    this.reset();
  }

  /**
   * Reset the subscription to flux and reset the current progress
   * */
  reset() {
    this.uploadProgress = 0;
    this.uploadSub = undefined;
  }

  /**
   * Allow the user to close the pop-up
   * */
  closePopUp(): void {
    console.log(this.formGroup.value)
    this.dialog.close();
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
