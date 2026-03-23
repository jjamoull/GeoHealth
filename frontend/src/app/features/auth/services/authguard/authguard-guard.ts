import { CanActivateFn, Router } from '@angular/router';
import {inject, PLATFORM_ID} from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { AuthService } from '../../../../core/service/AuthService/auth-service';
import {catchError, map, of} from 'rxjs';
import {LoginService} from '../../../../core/service/LoginService/loginService';

export const authGuard: CanActivateFn = () => {
  const platformId = inject(PLATFORM_ID);
  const authService = inject(AuthService);
  const router = inject(Router);
  const loginService= inject(LoginService);

  if (!isPlatformBrowser(platformId)) {
      return true;
    }

  return authService.isTokenValid().pipe(
    map(isValid => {
      if (!isValid) {
        router.navigate(["home"]);
      }
      loginService.setLoggedIn(isValid);
      return isValid;

    }),
    catchError(() => {
      router.navigate(["home"]);
      return of(false);

    })
  );
}
