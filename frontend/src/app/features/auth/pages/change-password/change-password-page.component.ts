import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {UserResponseDto} from '../../../../shared/models/UserModel/UserResponseDto';
import {UsersServices} from '../../../../core/service/UserService/users-services';
import {UpdatePasswordDto} from '../../../../shared/models/UserModel/UpdatePasswordDto';
import {InputboxComponents} from '../../../../shared/components/inputbox.components/inputbox.components';
import {ButtonComponent} from '../../../../shared/components/button.component/button.component';
import {TranslocoPipe} from '@jsverse/transloco';

@Component({
  selector: 'app-changepassword',
  imports: [
    ReactiveFormsModule,
    InputboxComponents,
    ButtonComponent,
    TranslocoPipe
  ],
  templateUrl: './change-password-page.component.html',
  styleUrl: './change-password-page.component.css',
})
export class ChangePasswordPageComponent implements OnInit{

  private userReponseDto:UserResponseDto={
    username: '',
    firstName: '',
    lastName: '',
    email: '',
    role:''
  }
  /**
   * Init the form to add a new user/account to the database
   */
  formGroup!: FormGroup;


  public messageSuccess:string|null=null;

  public messageError:string|null=null;

  public passwordForm!: FormGroup;

  constructor(private userService:UsersServices,private cdr: ChangeDetectorRef){}

  ngOnInit(): void{
    this.userService.getConnectedUser().subscribe(
      userData=>this.userReponseDto=userData

    )

    this.passwordForm = new FormGroup({
      oldPassword:new FormControl('', [Validators.required, Validators.minLength(8)]),
      newPassword: new FormControl('', [Validators.required, Validators.minLength(8)]),
      confirmNewPassword: new FormControl('', [Validators.required, Validators.minLength(8)])
    })

    this.cdr.detectChanges();
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
        this.messageSuccess='Changing password successful!';
        this.messageError=null;
        this.cdr.detectChanges();

      },
      error: (err) => {
        console.error('Error while changing password', err);
        this.messageError=err.error.message;
        this.messageSuccess=null;
        this.cdr.detectChanges();
      }
    });
  }
  /**
   * @param : name of the form field
   * @return : boolean true if minlength is not respected
   * */
  passwordLength(name: string): boolean {
    if (this.passwordForm==null){
      return false;
    }
    const control = this.passwordForm.get(name);
    if (!control || !control.value) return false;

    return !!control.errors?.['minlength'];
  }

  /**
   * @return : boolean true if password and confirmPassword do not match
   * */
  passwordsMatch(): boolean {
    if (this.passwordForm==null){
      return false;
    }
    const password = this.passwordForm.get("newPassword")?.value
    const confirmPassword = this.passwordForm.get("confirmNewPassword")?.value

    return !!confirmPassword && password !== confirmPassword;
  }



}
