import {ChangeDetectorRef, Component, inject} from '@angular/core';
import {Router} from '@angular/router';
import {LoginService} from '../../../core/service/LoginService/loginService';
import {TranslocoService} from '@jsverse/transloco';

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
  private transloco = inject(TranslocoService);

  switchLang(lang: string) {
    this.transloco.setActiveLang(lang);
  }



}
