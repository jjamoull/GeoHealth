import {ChangeDetectorRef, Component} from '@angular/core';
import {Router} from '@angular/router';
import {LoginService} from '../../../core/service/LoginService/loginService';

@Component({
  selector: 'app-unconnectednavbar',
  imports: [],
  templateUrl: './unconnectednavbar.html',
  styleUrl: './unconnectednavbar.css',
})
export class Unconnectednavbar {

  constructor(private router: Router,) {}

  goToLogin(){
    this.router.navigate(['login'])
  }

  goToRegister(){
    this.router.navigate(['register'])
  }
}
