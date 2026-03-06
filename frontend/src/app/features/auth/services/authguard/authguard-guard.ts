import { CanActivateFn, Router } from '@angular/router';
import {ChangeDetectorRef, inject} from '@angular/core';
import { AuthService } from '../../../../core/service/AuthService/auth-service';
import {routes} from '../../../../app.routes';
import {catchError, map, of} from 'rxjs';
import {LoginService} from '../../../../core/service/LoginService/loginService';

export const authGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const loginService= inject(LoginService);

  return authService.isTokenValid().pipe(
    map(isValid => {
      if (!isValid) {
        router.navigate(["login"]);
      }
      loginService.setLoggedIn(isValid);
      return isValid;

    }),
    catchError(() => {
      router.navigate(["login"]);
      return of(false);

    })
  );
}
