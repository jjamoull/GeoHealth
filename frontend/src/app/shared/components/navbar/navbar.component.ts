import {ChangeDetectorRef, Component, signal} from '@angular/core';
import {ActivatedRoute, Router, RouterOutlet} from '@angular/router';
import {LoginService} from '../../../core/service/LoginService/loginService';
import {catchError, map, Observable, of} from 'rxjs';
import {AuthService} from '../../../core/service/AuthService/auth-service';

@Component({
  selector: 'app-navbar',
  imports: [RouterOutlet],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
  standalone: true,
})
export class NavbarComponent {
  protected readonly title = signal('GeoHealth_Angular');


  constructor(public route: ActivatedRoute, private router: Router, private loginService:LoginService, private authService: AuthService,private cdr: ChangeDetectorRef) {}


  goToHome() {
    this.router.navigate(['home'])
  }
  goToNavigation(){
    this.router.navigate(['navigation'])
  }

  goToRegister(){
    this.router.navigate(['register'])
  }

  goToLogin(){
    this.router.navigate(['login'])
  }

  goToProfile(){
    this.router.navigate(['profile'])
  }

  logout():void {
    this.loginService.logout().subscribe({
      next: (response) => {
        console.log("logout");
        this.router.navigate(['login'])
      },
      error: (err) => {
        console.error('Error while logged out', err);
      }
    });
  }

}

