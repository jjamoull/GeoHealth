import { Routes } from '@angular/router';

import { LoginPageComponent } from './pages/login/login-page.component';
import { RegisterPageComponent } from './pages/register/register-page.component';
import { ForgotpasswordPageComponent } from './pages/forgotpassword/forgotpassword-page.component';
import { ResetpasswordPageComponent } from './pages/resetpassword/resetpassword-page.component';

export const AUTH_ROUTES: Routes = [

  {
    path: 'login',
    loadComponent: () =>
      import('./pages/login/login-page.component')
        .then(m => m.LoginPageComponent)
  },

  {
    path: 'register',
    loadComponent: () =>
      import('./pages/register/register-page.component')
        .then(m => m.RegisterPageComponent)
  },

  {
    path: 'forgot-password',
    loadComponent: () =>
      import('./pages/forgotpassword/forgotpassword-page.component')
        .then(m => m.ForgotpasswordPageComponent)
  },

  {
    path: 'reset-password',
    loadComponent: () =>
      import('./pages/resetpassword/resetpassword-page.component')
        .then(m => m.ResetpasswordPageComponent)
  }

];
