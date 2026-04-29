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
  public isOpen:boolean = false;
  goToHome() {
    this.router.navigate(['home'])
    this.isOpen = false;
  }

  goToLogin() {
    this.router.navigate(['login']);
    this.isOpen = false;
  }

  goToRegister() {
    this.router.navigate(['register']);
    this.isOpen = false;
  }

}
