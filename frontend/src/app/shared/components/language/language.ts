import {Component, inject, OnInit, ElementRef, HostListener} from '@angular/core';
import {Router} from '@angular/router';
import {TranslocoService} from '@jsverse/transloco';

@Component({
  selector: 'app-language',
  imports: [],
  templateUrl: './language.html',
  styleUrl: './language.css',
})
export class Language implements OnInit {

  constructor(private router: Router, private el: ElementRef) {}

  private transloco = inject(TranslocoService);

  selectedLang = "fr";
  isOpen = false;
  langs = [
    { code: 'fr', flag: 'assets/icons/frFlag.webp' },
    { code: 'en', flag: 'assets/icons/enFlag.webp' },
  ];

  ngOnInit() {
    if (typeof window !== 'undefined') {
      const savedLang = localStorage.getItem('selectedLang');
      if (savedLang) {
        this.selectedLang = savedLang;
        this.transloco.setActiveLang(this.selectedLang);
      }
    }
  }

  switchLang(lang: string) {
    this.selectedLang = lang;
    this.transloco.setActiveLang(lang);
    localStorage.setItem('selectedLang', lang);
  }

  select(lang: string) {
    this.switchLang(lang);
    this.isOpen = false;
  }

  @HostListener('document:click', ['$event'])
  onClickOutside(event: MouseEvent) {
    if (!this.el.nativeElement.contains(event.target)) {
      this.isOpen = false;
    }
  }
}
