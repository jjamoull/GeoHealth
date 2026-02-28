import {CanActivateFn, Router} from '@angular/router';
import {UsersServices} from '../../../core/service/UserService/users-services';
import {inject} from '@angular/core';
import {catchError, map, of} from 'rxjs';

/**
 * Guard that check if a user is aan admin or not.
 * If admin allow to go to the route you want, otherwise redirect to home.
 */
export const adminGuard: CanActivateFn = (route, state) => {

  const usersService= inject(UsersServices);
  const router = inject(Router);

  return usersService.getConnectedUser().pipe(
    map(user => {
      if (user.role === "ADMIN" || user.role==="SUPERADMIN"){
        return true;
      }
      router.navigate(['home']);
      return false;

    }),
    catchError(() => {
      router.navigate(['home']);
      return of(false);
    })
  );

};
