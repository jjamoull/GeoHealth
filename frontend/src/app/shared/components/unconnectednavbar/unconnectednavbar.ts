import {ChangeDetectorRef, Component, inject, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {LoginService} from '../../../core/service/LoginService/loginService';
import {TranslocoService} from '@jsverse/transloco';

@Component({
  selector: 'app-unconnectednavbar',
  imports: [],
  templateUrl: './unconnectednavbar.html',
  styleUrl: './unconnectednavbar.css',
})
export class Unconnectednavbar implements OnInit{

  constructor(private router: Router,) {}

  selectedLang = "en";

  /**
   * Collect the user information when the component initialize
   */
  ngOnInit() {
    if (typeof window !== 'undefined') {
      const savedLang = localStorage.getItem('selectedLang');
      if (savedLang) {
        this.selectedLang = savedLang;
        this.transloco.setActiveLang(this.selectedLang);
      }
    }
  }
  goToHome() {
    this.router.navigate(['home'])
  }

  goToLogin(){
    this.router.navigate(['login'])
  }

  goToRegister(){
    this.router.navigate(['register'])
  }
  private transloco = inject(TranslocoService);

  switchLang(lang: string) {
    this.selectedLang = lang;
    this.transloco.setActiveLang(lang);
    localStorage.setItem('selectedLang', lang);
  }



}
