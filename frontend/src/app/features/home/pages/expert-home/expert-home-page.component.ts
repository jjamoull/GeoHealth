import { Component } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import { Router } from '@angular/router';
import {LoginService} from '../../../../core/service/LoginService/loginService';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './expert-home-page.component.html',
  styleUrl: './expert-home-page.component.css',
  standalone: true,
})
export class ExpertHomePageComponent {
  constructor(
    public route: ActivatedRoute,
    private router: Router,
    private loginService: LoginService
  ) {}

  /**
   * Go to "…:navigation/"
   * */
  goToNavigation(){
      this.router.navigate(['/navigation']);
  }

  logout(): void {
    this.loginService.logout().subscribe({
      next: (response) => {
        console.log("logged out");
        },
      error: (err) => {
        console.error('Error while logging out', err);
      }
    });
  }

  checkStatus(): void {
    this.loginService.checkStatus().subscribe({
      next: (response) => {
        console.log('Status:', response);
      },
      error: (err) => {
        if (err.status === 401){
          console.log('User is not authenticated');
        } else {
          console.error('Error while checking status', err);
        }
      }
    });
  }
}
