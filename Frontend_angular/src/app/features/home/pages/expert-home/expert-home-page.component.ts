import { Component } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './expert-home-page.component.html',
  styleUrl: './expert-home-page.component.css',
  standalone: true,
})
export class ExpertHomePageComponent {
  constructor(public route: ActivatedRoute, private router: Router) {}

  /**
   * Go to "…:navigation/"
   * */
  goToNavigation(){
      this.router.navigate(['/navigation']);
  }

}
