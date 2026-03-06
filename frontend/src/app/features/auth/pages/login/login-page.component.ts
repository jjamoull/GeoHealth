import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {LoginService} from '../../../../core/service/LoginService/loginService';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import {ButtonComponent} from '../../../../shared/components/button.component/button.component';
import {InputboxComponents} from '../../../../shared/components/inputbox.components/inputbox.components';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule,
    ButtonComponent,
    InputboxComponents,
  ],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css',
})
export class LoginPageComponent implements OnInit {

  formGroup!: FormGroup;
  errorMessage: string | null = null;
  loginError = false;

  constructor(
    private loginService: LoginService,
    private cdr:ChangeDetectorRef,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.formGroup = new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required])
    });
  }

  /**
   * Request login to the backend with formGroup information
   */
  public login(): void {
    if (this.formGroup.invalid) {
      this.formGroup.markAllAsTouched();
      return;
    }

    this.errorMessage = null;
    this.loginError = false;

    this.loginService.login(this.formGroup.value).subscribe({
      next: () => {
        this.router.navigate(['/home']);
        this.loginService.setLoggedIn(true);
        this.cdr.detectChanges();
      },
      error: (err) => {
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
