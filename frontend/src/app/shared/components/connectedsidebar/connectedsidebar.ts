import {ChangeDetectorRef, Component, HostListener} from '@angular/core';
import {Router} from '@angular/router';
import {LoginService} from '../../../core/service/LoginService/loginService';
import {UsersServices} from '../../../core/service/UserService/users-services';
import {Language} from '../language/language';
import {TranslocoPipe} from '@jsverse/transloco';

@Component({
  selector: 'app-connectedsidebar',
  imports: [
    Language,
    TranslocoPipe
  ],
  templateUrl: './connectedsidebar.html',
  styleUrl: './connectedsidebar.css',
})
export class Connectedsidebar {
  public isAdmin:boolean=false;
  public isOpen:boolean = false;

  isDesktop = typeof window !== 'undefined' && window.innerWidth > 768;

  @HostListener('window:resize')
  onResize() {
    this.isDesktop = window.innerWidth > 768;
  }

  constructor(private router: Router,
              public loginService:LoginService,
              private usersServices: UsersServices,
              private cdr:ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.usersServices.isAdmin().subscribe(
      bool =>{
        this.isAdmin=bool
        this.cdr.detectChanges();
      }
    );

  }
  goToNavigation(){
    this.router.navigate(['navigation'])
    this.isOpen = false;
  }

  goToProfile(){
    this.router.navigate(['profile'])
    this.isOpen = false;
  }

  goToUsersList(){
    this.router.navigate(['users-list'])
    this.isOpen = false;
  }

  logout():void {
    this.loginService.logout().subscribe({
      next: (response) => {
        console.log("logout");
        this.loginService.setLoggedIn(false);
        this.router.navigate(['login']);
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error while logged out', err);
        this.cdr.detectChanges();
      }
    });
    this.isOpen = false;
  }


}
