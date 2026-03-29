import {ChangeDetectorRef, Component, inject, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {LoginService} from '../../../core/service/LoginService/loginService';

@Component({
  selector: 'app-unconnectednavbar',
  imports: [],
  templateUrl: './unconnectednavbar.html',
  styleUrl: './unconnectednavbar.css',
})
export class Unconnectednavbar{

  constructor(private router: Router,) {}


  goToHome() {
    this.router.navigate(['home'])
  }

  goToLogin(){
    this.router.navigate(['login'])
  }

  goToRegister(){
    this.router.navigate(['register'])
  }
}
