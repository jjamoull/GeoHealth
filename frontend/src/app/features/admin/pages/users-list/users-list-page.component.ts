import {ChangeDetectorRef, Component} from '@angular/core';
import {OnInit} from '@angular/core';
import {AdminsServices} from '../../../../core/service/AdminService/admins-services';
import {ButtonComponent} from '../../../../shared/components/button.component/button.component';
import {BanDto} from '../../../../shared/models/UserModel/BanDto';
import {AdminUserDto} from '../../../../shared/models/UserModel/AdminUserDto';


@Component({
  selector: 'app-users-list',
  imports: [
    ButtonComponent
  ],
  templateUrl: './users-list-page.component.html',
  styleUrl: './users-list-page.component.css',
})
export class UsersListPageComponent implements  OnInit{
  users: AdminUserDto[] = [];

  constructor(private adminsServices: AdminsServices, private cdr: ChangeDetectorRef) {}


  ngOnInit() {
    this.getAllUsers();
  }

  /**
   * @effect :
   * @return :
   * */
  private getAllUsers(){
    this.adminsServices.getAllUsers().subscribe(
      data =>{
        console.log(data);
        this.users = data;
        this.cdr.detectChanges();
      })
  }

  public banUser(name:string){
    let banDto:BanDto={
      username:name,
      reason:'',
    };

    this.adminsServices.banUser(banDto).subscribe({
      next: (response) => {
        console.log('Utilisateur banni avec succès', response);
      },
      error: (err) => {
        console.error('Erreur lors du bannissement', err);
      }
    });
  }

}
