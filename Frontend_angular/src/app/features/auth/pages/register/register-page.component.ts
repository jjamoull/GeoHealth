import {Component, OnInit} from '@angular/core';
import {LoginService} from '../Service/LoginService/loginService';
import {User} from '../Model/UserListModel/User';
import {throwError} from "rxjs";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule
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
      username: new FormControl('', [Validators.required, Validators.minLength(1)]),
      firstName: new FormControl('', [Validators.required, Validators.minLength(1)]),
      lastName: new FormControl('', [Validators.required, Validators.minLength(1)]),
      email: new FormControl('', [Validators.required, Validators.minLength(3), Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(8)]),
      confirmPassword : new FormControl('', [Validators.required, Validators.minLength(8)])
    });
  }


  constructor(private LoginService: LoginService) {
  }

  /**
   * @modifies : User with the new data that the user has recorded
   * */
  public addUser(): void {

    if (this.formGroup.invalid) {
      this.formGroup.markAllAsTouched();
      return;
    }

    //retrieves confirmPassword from the form to match the User format
    const formValue = this.formGroup.value;
    const user: User = {
      id: 0,
      username: formValue.username,
      firstName: formValue.firstName,
      lastName: formValue.lastName,
      email: formValue.email,
      password: formValue.password,
      role: 'Admin'
    };

    this.LoginService.addUser(user).subscribe({
      next: data => {
        this.Users = data;
        console.log('User created', data);
      },
      error: err => {
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


  showPassword = false;

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }

  isFieldValid(name: string){
    const formControl = this.formGroup.get(name);
    return formControl?.invalid && formControl?.dirty;
  }
}
