import { CanActivateFn, Router } from '@angular/router';
import { inject} from '@angular/core';
import { AuthService } from '../Service/AuthService/auth-service';
import {routes} from '../../app.routes';
import {catchError, map, of} from 'rxjs';

export const authGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router)

  return authService.isTokenValid().pipe(
    map(isValid => {
      console.log(isValid)
      if (!isValid) {
        router.navigate(["login"]);
      }
      return isValid;
    }),
    catchError(() => {
      router.navigate(["login"]);
      return of(false);
    })
  );
};
