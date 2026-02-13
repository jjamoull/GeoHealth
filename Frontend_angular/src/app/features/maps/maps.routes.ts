import { Routes } from '@angular/router';

import {NavigationPageComponent} from './pages/navigation/navigation-page.component';

export const MAPS_ROUTES: Routes = [
  {
    path: 'navigation',
    loadComponent: () =>
      import('./pages/navigation/navigation-page.component')
        .then(m => m.NavigationPageComponent)
  },
]
