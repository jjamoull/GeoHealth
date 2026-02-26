import {ChangeDetectorRef, Component, OnInit, signal} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {NavbarComponent} from './shared/components/navbar/navbar.component';
import {AuthService} from './core/service/AuthService/auth-service';
import {LoginService} from './core/service/LoginService/loginService';
import {catchError, map, of} from 'rxjs';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NavbarComponent],
  templateUrl: './app.html',
  styleUrl: './app.css',
  standalone: true,
})
export class App implements OnInit{
  protected readonly title = signal('GeoHealth_Angular');

  constructor(private authService:AuthService, private loginService:LoginService,private cdr:ChangeDetectorRef) {
  }
  ngOnInit() {
    this.authService.isTokenValid().pipe(
      map(isValid => {
        this.loginService.setLoggedIn(isValid);
        this.cdr.detectChanges();
      })
    ).subscribe();
  }
}
