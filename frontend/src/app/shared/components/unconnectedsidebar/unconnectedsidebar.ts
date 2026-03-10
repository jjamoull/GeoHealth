import { Component } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-unconnectedsidebar',
  imports: [],
  templateUrl: './unconnectedsidebar.html',
  styleUrl: './unconnectedsidebar.css',
})
export class Unconnectedsidebar {
  constructor(private router: Router,) {}

  goToLogin() {
    this.router.navigate(['login']);
  }

  goToRegister() {
    this.router.navigate(['register']);
  }

}
