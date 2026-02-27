import {Component, OnInit} from '@angular/core';
import {LoginService} from '../../../../core/service/LoginService/loginService';
import {User} from '../../../../shared/models/UserModel/User';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { CommonModule } from '@angular/common';
import {Router} from '@angular/router';
import {ButtonComponent} from '../../../../shared/components/button.component/button.component';
import {InputboxComponents} from '../../../../shared/components/inputbox.components/inputbox.components';




@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule,
    ButtonComponent,
    InputboxComponents
  ],
  templateUrl: './register-page.component.html',
  styleUrl: './register-page.component.css',
})
export class RegisterPageComponent implements  OnInit {

  Users: User = {
    id: 0,
    username: "anonymous",
    firstName: "anonymous",
    lastName: "anonymous",
    password: "anonymous",
    email: "anonymous@email",
    role: "Admin"
  };

  /**
   * Init the form to add a new user/account to the database
   */
  formGroup!: FormGroup;


  ngOnInit(): void {
    this.formGroup = new FormGroup({
      username: new FormControl('', [Validators.required, Validators.minLength(3)]),
      firstName: new FormControl('', [Validators.required, Validators.minLength(3)]),
      lastName: new FormControl('', [Validators.required, Validators.minLength(3)]),
      email: new FormControl('', [Validators.required, Validators.minLength(3), Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(8)]),
      confirmPassword : new FormControl('', [Validators.required, Validators.minLength(8)])
    });
  }


  constructor(private LoginService: LoginService, private router: Router) {
  }
  /**
   * Redirection to login page
   */
  goToLogin(){
    this.router.navigate(['login'])
  }

  /**
   * Redirection to home page
   */
  goToHome(){
    this.router.navigate(['home'])
  }

  /**
   * @modifies : User with the new data that the user has recorded
   * */
  public register(): void {
    if (this.formGroup.invalid) {
      this.formGroup.markAllAsTouched();
      return;
    }

    if (!this.checkPasswordConformity()) {
      console.error('Passwords do not match');
      return;
    }

    const formValue = this.formGroup.value;

    const registerDto = {
      username: formValue.username,
      firstName: formValue.firstName,
      lastName: formValue.lastName,
      email: formValue.email,
      password: formValue.password
    };

    this.LoginService.register(registerDto).subscribe({
      next: (response) => {
        console.log('Registration successful!', response);
        this.goToHome()
      },
      error: (err) => {
        console.error('Error while creating user', err);
      }
    });
  }

  /**
   *  @return true if password = confirmPassword, false otherwise
   * */
  public checkPasswordConformity(): boolean {
    if (this.formGroup==null){
      return false;
    }

    const passwordControl = this.formGroup.get('password');
    const confirmPasswordControl = this.formGroup.get('confirmPassword');
    if (passwordControl ==null || confirmPasswordControl== null){
      return false
    }

    return confirmPasswordControl.value == passwordControl.value;
  }
  nameLength(name: string): boolean {
    const control = this.formGroup.get(name);
    if (!control || !control.value) return false;

    return !!control.errors?.['minlength'];
  }

  mailLength(name: string): boolean {
    const control = this.formGroup.get(name);
    if (!control || !control.value) return false;

    return !!control.errors?.['email'];
  }
  passwordLength(name: string): boolean {
    const control = this.formGroup.get(name);
    if (!control || !control.value) return false;

    return !!control.errors?.['minlength'];
  }

  passwordsMatch(): boolean {
    const password = this.formGroup.get("password")?.value
    const confirmPassword = this.formGroup.get("confirmPassword")?.value

    return !!confirmPassword && password !== confirmPassword;
  }
}
