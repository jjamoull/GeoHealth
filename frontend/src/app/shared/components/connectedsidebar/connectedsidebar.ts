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
  public isOpen:boolean = false;

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
