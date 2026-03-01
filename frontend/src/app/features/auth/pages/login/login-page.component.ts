import {Component, OnInit} from '@angular/core';
import {LoginService} from '../../../../core/service/LoginService/loginService';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import {ButtonComponent} from '../../../../shared/components/button.component/button.component';
import {InputboxComponents} from '../../../../shared/components/inputbox.components/inputbox.components';
import {ErrorSuccessMessageComponent} from '../../../../shared/components/error-success-message.component/error-success-message.component';
import { signal } from '@angular/core';



@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule,
    ButtonComponent,
    InputboxComponents,
    ErrorSuccessMessageComponent
  ],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css',
})
export class LoginPageComponent implements OnInit {

  formGroup!: FormGroup;
  loginError = signal(false);
  errorMessage = signal('');
  successMessage = signal('');

  constructor(
    private LoginService: LoginService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.formGroup = new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required])
    });
  }

  public login(): void {
    if (this.formGroup.invalid) {
      this.formGroup.markAllAsTouched();
      return;
    }

    this.LoginService.login(this.formGroup.value).subscribe({
      next: () => {
        // Ok -> redirect to /home
        this.router.navigate(['/home']);
        this.loginError.set(false);
        this.errorMessage.set('');
      },
      error: () => {
        // Display error message
        this.loginError.set(true);
        this.errorMessage.set('username or password invalid');

      }
    });
  }

  /**
   * Redirection to register page
   */
  goToRegister(){
    this.router.navigate(['register'])
  }

}
