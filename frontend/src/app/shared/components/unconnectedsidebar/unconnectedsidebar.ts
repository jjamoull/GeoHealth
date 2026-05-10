import {Component, HostListener} from '@angular/core';
import {Router} from '@angular/router';
import {Language} from "../language/language";
import {TranslocoPipe} from '@jsverse/transloco';

@Component({
  selector: 'app-unconnectedsidebar',
  imports: [
    Language,
    TranslocoPipe
  ],
  templateUrl: './unconnectedsidebar.html',
  styleUrl: './unconnectedsidebar.css',
})
export class Unconnectedsidebar {
  constructor(private router: Router,) {}
  public isOpen:boolean = false;

  isDesktop = typeof window !== 'undefined' && window.innerWidth > 768;

  @HostListener('window:resize')
  onResize() {
    this.isDesktop = window.innerWidth > 768;
  }

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
