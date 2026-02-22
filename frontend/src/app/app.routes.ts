import { Routes } from '@angular/router';
import {ExpertHomePageComponent} from './features/home/pages/expert-home/expert-home-page.component';
import {MapComponent} from './shared/components/map/map.component';
import {NavigationPageComponent} from './features/navigation/pages/navigation/navigation-page.component';
import { LoginPageComponent } from './features/auth/pages/login/login-page.component';
import { RegisterPageComponent } from './features/auth/pages/register/register-page.component';
import { ForgotPasswordPageComponent } from './features/auth/pages/forgot-password/forgot-password-page.component';
import { ResetPasswordPageComponent } from './features/auth/pages/reset-password/reset-password-page.component';
import {UsersListPageComponent} from './features/admin/pages/users-list/users-list-page.component';
import { authGuard } from './features/auth/services/authguard/authguard-guard';
import {ProfilePageComponent} from './features/auth/pages/profile/profile-page.component';
import {ChangePasswordPageComponent} from './features/auth/pages/change-password/change-password-page.component';

export const routes: Routes = [
  {
    path: 'home',
    component: ExpertHomePageComponent,
    canActivate: [authGuard],
  },{
    path: 'map/:id',
    component: MapComponent,
    canActivate: [authGuard],
  },{
    path:'navigation',
    component: NavigationPageComponent,
    canActivate: [authGuard]
  },{
    path: 'usersList',
    component: UsersListPageComponent,
    canActivate: [authGuard],
  },{
  path: '',
  redirectTo: 'login',
  pathMatch: 'full'
  },{
    path:'login',
    component: LoginPageComponent,
  },{
    path:'register',
    component: RegisterPageComponent,
  },{
    path:'forgot-password',
    component: ForgotPasswordPageComponent,
  },{
    path:'reset-password',
    component: ResetPasswordPageComponent,
  },{
    path:'profile',
    component: ProfilePageComponent,
    canActivate: [authGuard]
  },{
    path:'change-password',
    component: ChangePasswordPageComponent,
  },{
    path: '**',
    redirectTo: 'login',
    pathMatch: 'full'
  }
];


