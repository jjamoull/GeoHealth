import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {LoginService} from '../../../core/service/LoginService/loginService';
import {UsersServices} from '../../../core/service/UserService/users-services';

@Component({
  selector: 'app-connectednavbar',
  imports: [],
  templateUrl: './connectednavbar.html',
  styleUrl: './connectednavbar.css',
})
export class Connectednavbar implements OnInit{

  public isAdmin:boolean=false;

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
        this.router.navigate(['home']);
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error while logged out', err);
        this.cdr.detectChanges();
      }
    });
  }


}
