import {Component, OnInit} from '@angular/core';
import {LoginService} from '../../../../core/service/LoginService/loginService';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import {ButtonComponent} from '../../../../shared/components/button.component/button.component';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule,
    ButtonComponent
  ],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css',
})
export class LoginPageComponent implements OnInit {

  formGroup!: FormGroup;
  errorMessage: string | null = null;
  loginError = false;

  constructor(
    private LoginService: LoginService,
    private router: Router
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

    this.errorMessage = null;
    this.loginError = false;

    this.LoginService.login(this.formGroup.value).subscribe({
      next: () => {
        // Ok -> redirect to /home
        this.router.navigate(['/home']);
      },
      error: (err) => {
        // Display error message
        this.loginError = true;

        if (err.status === 401) {
          this.errorMessage = "Identifiants invalides.";
        } else {
          this.errorMessage = "Erreur serveur. Réessayez.";
        }
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
