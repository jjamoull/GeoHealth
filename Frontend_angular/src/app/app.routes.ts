import { Routes } from '@angular/router';
import {Home} from './components/home/home';
import {Authentication} from './components/authentication/authentication';
import {Map} from './components/map/map';

export const routes: Routes = [
  {
    path: 'home',
    component: Home,
  },{
    path: 'authentification',
    component: Authentication,
  },{
    path: 'map',
    component: Map,
  },{
  path: '',
  redirectTo: 'home',
  pathMatch: 'full'
  },{
    path: '**',
    redirectTo: 'home',
    pathMatch: 'full'
  }
];
