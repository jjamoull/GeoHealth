import { Routes } from '@angular/router';
import {Home} from './components/home/home';
import {Map} from './components/map/map';
import {Navigation} from './components/navigation/navigation';
import { Login } from './components/login/login';
import { Register } from './components/register/register';
import { Forgotpassword } from './components/forgotpassword/forgotpassword';
import { Resetpassword } from './components/resetpassword/resetpassword';
import { Logout } from './components/logout/logout';


export const routes: Routes = [
  {
    path: 'home',
    component: Home,
  },{
    path: 'map',
    component: Map,
  },{
    path:'navigation',
    component: Navigation,
  }
  ,{
  path: '',
  redirectTo: 'home',
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
    path:'logout',
    component: Logout,
  },{
    path: '**',
    redirectTo: 'home',
    pathMatch: 'full'
  }
];
