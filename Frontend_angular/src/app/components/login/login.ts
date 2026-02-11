import {Component, OnInit} from '@angular/core';
import {LoginService} from '../Service/LoginService/loginService';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule
  ],
  templateUrl: './login.html',
  styleUrl: './login.css',
})

export class Login implements OnInit {

  /**
   * Init the form to login
   */
  formGroup!: FormGroup;

  ngOnInit(): void {
    this.formGroup = new FormGroup({
      username: new FormControl('', [Validators.required, Validators.minLength(1)]),
      password: new FormControl('', [Validators.required, Validators.minLength(1)])
    });
  }

  constructor(private LoginService: LoginService) {}

  /**
   * Login user
   */
  public login(): void {
    if (this.formGroup.invalid) {
      this.formGroup.markAllAsTouched();
      return;
    }

    const formValue = this.formGroup.value;

    const loginDto = {
      username: formValue.username,
      password: formValue.password
    };

    this.LoginService.login(loginDto).subscribe({
      next: (response) => {
        console.log('Login successful!', response);
        console.log('TOKEN:', response.token);

        localStorage.setItem('token', response.token);
        console.log(localStorage.getItem('token'));
      },
      error: (err) => {
        console.error('Error while logging in', err);
      }
    });
  }

}
