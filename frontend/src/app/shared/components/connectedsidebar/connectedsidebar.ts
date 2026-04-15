import {ChangeDetectorRef, Component} from '@angular/core';
import {Router} from '@angular/router';
import {LoginService} from '../../../core/service/LoginService/loginService';
import {UsersServices} from '../../../core/service/UserService/users-services';

@Component({
  selector: 'app-connectedsidebar',
  imports: [],
  templateUrl: './connectedsidebar.html',
  styleUrl: './connectedsidebar.css',
})
export class Connectedsidebar {
  public isAdmin:boolean=false;
  public isOpen:boolean = true;

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

  goToHome() {
    this.router.navigate(['home'])
  }
  goToNavigation(){
    this.router.navigate(['navigation'])
  }

  goToProfile(){
    this.router.navigate(['profile'])
  }

  goToUsersList(){
    this.router.navigate(['users-list'])
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
  }


}
