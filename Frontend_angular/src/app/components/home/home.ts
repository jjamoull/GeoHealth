import { Component } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import {ActivatedRoute} from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.html',
  styleUrl: './home.css',
  standalone: true,
})
export class Home {

  constructor(public route: ActivatedRoute, private router: Router) {}

  /**
   * Go to "…:navigation/"
   * */
  goToNavigation(){
    this.router.navigate(['/navigation']);
  }

  /**
   * Go to "…:login/"
   * */
  goToLogin(){
    this.router.navigate(['/login']);
  }

}
