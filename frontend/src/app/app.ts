import {ChangeDetectorRef, Component, signal} from '@angular/core';
import {ActivatedRoute, Router, RouterOutlet} from '@angular/router';
import {LoginService} from './service/LoginService/loginService';
import {catchError, map, Observable, of} from 'rxjs';
import {AuthService} from './service/AuthService/auth-service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css',
  standalone: true,
})
export class App {
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
