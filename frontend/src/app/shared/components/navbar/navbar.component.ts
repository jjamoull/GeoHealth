import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {LoginService} from '../../../core/service/LoginService/loginService';
import {UsersServices} from '../../../core/service/UserService/users-services';
import {UserResponseDto} from '../../models/UserModel/UserResponseDto';
import {AdminsServices} from '../../../core/service/AdminService/admins-services';
import {Observable} from 'rxjs';


@Component({
  selector: 'app-navbar',
  imports: [],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
  standalone: true,
})
export class NavbarComponent {

  constructor(private router: Router,
              public loginService:LoginService,
              private cdr:ChangeDetectorRef,
  ) {}



  goToHome() {
    this.router.navigate(['home'])
  }
  goToNavigation(){
    this.router.navigate(['navigation'])
  }

  goToLogin(){
    this.router.navigate(['login'])
  }

  goToProfile(){
    this.router.navigate(['profile'])
  }

  goToUsersList(){
    this.router.navigate(['users-list'])
  }

  logout():void {
    this.loginService.logout().subscribe({
      next: (response) => {
        console.log("logout");
        this.loginService.setLoggedIn(false);
        this.router.navigate(['login']);
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error while logged out', err);
        this.cdr.detectChanges();
      }
    });
  }

  isLoggedIn():boolean{
    return this.loginService.isLoggedIn();
  }

}

