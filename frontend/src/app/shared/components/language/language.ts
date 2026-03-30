import {Component, inject, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {TranslocoService} from '@jsverse/transloco';
@Component({
  selector: 'app-language',
  imports: [],
  templateUrl: './language.html',
  styleUrl: './language.css',
})
export class Language implements OnInit{
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
  private transloco = inject(TranslocoService);

  switchLang(lang: string) {
    this.selectedLang = lang;
    this.transloco.setActiveLang(lang);
    localStorage.setItem('selectedLang', lang);
  }


}
