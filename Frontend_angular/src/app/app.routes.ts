import { Routes } from '@angular/router';
import {Home} from './components/home/home';
import {Map} from './components/map/map';
import {Navigation} from './components/navigation/navigation';
import { Login } from './components/login/login';
import { Register } from './components/register/register';
import { Forgotpassword } from './components/forgotpassword/forgotpassword';
import { Resetpassword } from './components/resetpassword/resetpassword';
import {UsersList} from './components/users-list/users-list';
import { authGuard } from './components/authguard/authguard-guard';

export const routes: Routes = [
  {
    path: 'home',
    component: Home,
    canActivate: [authGuard],
  },{
    path: 'map',
    component: Map,
    canActivate: [authGuard],
  },{
    path:'navigation',
    component: Navigation,
  },{
    path: 'usersList',
    component: UsersList,
    canActivate: [authGuard],
  },{
  path: '',
  redirectTo: 'login',
  pathMatch: 'full'
  },{
    path:'login',
    component: Login,
  },{
    path:'register',
    component: Register,
  },{
    path:'forgot-password',
    component: Forgotpassword,
  },{
    path:'reset-password',
    component: Resetpassword,
  },{
    path: '**',
    redirectTo: 'login',
    pathMatch: 'full'
  }
];
