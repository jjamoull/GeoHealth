import { Component, signal } from '@angular/core';
import {ActivatedRoute, Router, RouterOutlet} from '@angular/router';
import {Home} from './components/home/home';
import {Map} from './components/map/map';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css',
  standalone: true,
})
export class App {
  protected readonly title = signal('GeoHealth_Angular');

  constructor(public route: ActivatedRoute, private router: Router) {}



  goToHome() {
    this.router.navigate(['home'])
  }

  goToNavigation(){
    this.router.navigate(['navigation'])
  }

  goToLogin(){
    this.router.navigate(['register'])
  }


}
