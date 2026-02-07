import { Routes } from '@angular/router';
import {Home} from './components/home/home';
import {Authentication} from './components/authentication/authentication';
import {Map} from './components/map/map';
import {Navigation} from './components/navigation/navigation';

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
    path:'navigation',
    component: Navigation,
  }
  ,{
  path: '',
  redirectTo: 'home',
  pathMatch: 'full'
  },{
    path: '**',
    redirectTo: 'home',
    pathMatch: 'full'
  }
];
