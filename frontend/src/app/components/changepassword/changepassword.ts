import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {UserResponseDto} from '../../model/UserModel/UserResponseDto';
import {UsersServices} from '../../service/UserService/users-services';
import {UpdatePasswordDto} from '../../model/UserModel/UpdatePasswordDto';

@Component({
  selector: 'app-changepassword',
    imports: [
        ReactiveFormsModule
    ],
  templateUrl: './changepassword.html',
  styleUrl: './changepassword.css',
})
export class Changepassword implements OnInit{

  private userReponseDto:UserResponseDto={
    username: '',
    firstName: '',
    lastName: '',
    email: '',
    role:''
  }

  public passwordShowing={
    oldPassword: false,
    newPassword: false,
    confirmNewPassword: false,
  }

  public passwordForm!: FormGroup;

  constructor(private userService:UsersServices){}

  ngOnInit(): void{
    this.userService.getConnectedUser().subscribe(
      userData=>this.userReponseDto=userData
    )

    this.passwordForm = new FormGroup({
      oldPassword:new FormControl('', [Validators.required, Validators.minLength(8)]),
      newPassword: new FormControl('', [Validators.required, Validators.minLength(8)]),
      confirmNewPassword: new FormControl('', [Validators.required, Validators.minLength(8)])
    })
  }

  /**
   * Check if a field respects the conventions or not
   *
   * @param name name of the field to check
   * @return true if field is valid, false otherwise
   */
  public isFieldValid(name: string):boolean|undefined{
    const formControl = this.passwordForm.get(name);
    return formControl?.invalid && formControl?.dirty;
  }

  /**
   * Toggle the value of the field
   *
   * @param field the passwordShowing field we want to toggle
   * @modifies passwordShowing
   * @effect toggle the value of the selected field
   */
  public togglePasswordShowing(field: keyof typeof this.passwordShowing){
    this.passwordShowing[field] = !this.passwordShowing[field];
  }


  /**
   *  Check if password match or not
   *
   *  @return true if password == confirmPassword, false otherwise
   * */
  public checkPasswordConformity(): boolean {
    if (this.passwordForm==null){
      return false;
    }

    const passwordControl = this.passwordForm.get('newPassword');
      const confirmPasswordControl = this.passwordForm.get('confirmNewPassword');
      if (passwordControl ==null || confirmPasswordControl== null){
        return false
      }

    return confirmPasswordControl.value == passwordControl.value;
  }

  /**
   * Collect the new password information and send them to the backend
   *
   * @effect: modifies password information
   */
  public submitPasswordInfo(){

    if (this.passwordForm.invalid) {
      this.passwordForm.markAllAsTouched();
      return;
    }

    if (!this.checkPasswordConformity()) {
      console.error('Passwords do not match');
      return;
    }

    const passwordValue = this.passwordForm.value;

    let updatePasswordDto:UpdatePasswordDto={
      username: this.userReponseDto.username,
      oldPassword: passwordValue.oldPassword,
      newPassword: passwordValue.newPassword
    };


    this.userService.changePassword(updatePasswordDto).subscribe({
      next: (response) => {
        console.log('Changing password successful!', response);
      },
      error: (err) => {
        console.error('Error while changing password', err);
      }
    });

  }


}
