import { Routes } from '@angular/router';
import { AUTH_ROUTES } from './features/auth/auth.routes';
import { HOME_ROUTES } from './features/home/home.routes';
import {MAPS_ROUTES} from './features/maps/maps.routes';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },

  {
    path: 'auth',
    children: AUTH_ROUTES
  },

  {
    path: 'home',
    children: HOME_ROUTES
  },

  {
    path: 'maps',
    children: MAPS_ROUTES
  }
];
