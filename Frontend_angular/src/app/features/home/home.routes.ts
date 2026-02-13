import { Routes } from '@angular/router';

import {ExpertHomePageComponent} from './pages/expert-home/expert-home-page.component';

export const HOME_ROUTES: Routes = [

  {
    path: '',
    loadComponent: () =>
      import('./pages/expert-home/expert-home-page.component')
        .then(m => m.ExpertHomePageComponent)
  },
];
