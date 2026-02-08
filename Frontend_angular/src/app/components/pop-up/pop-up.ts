import {Component, Inject, OnInit} from '@angular/core';
import {MatDialog,MatDialogRef, MatDialogModule, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';

@Component({
  selector: 'app-pop-up',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './pop-up.html',
  styleUrl: './pop-up.css',
})
export class PopUp implements OnInit{
  constructor(private dialog: MatDialogRef <PopUp>) {}
  /**
   * Allow the user to close the pop-up
   * */
  closePopUp(): void {
    console.log(this.formGroup.value)
    this.dialog.close();
  }

  formGroup!: FormGroup;
  ngOnInit():void {
    this.formGroup = new FormGroup({
      title: new FormControl('',[Validators.required, Validators.minLength(1)]),
      description: new FormControl('', [Validators.required, Validators.minLength(1)]),
      file: new FormControl()
    });
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
