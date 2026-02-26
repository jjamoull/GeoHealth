import {ChangeDetectorRef, Component} from '@angular/core';
import {OnInit} from '@angular/core';
import {AdminsServices} from '../../../../core/service/AdminService/admins-services';
import {ButtonComponent} from '../../../../shared/components/button.component/button.component';
import {BanDto} from '../../../../shared/models/UserModel/BanDto';
import {AdminUserDto} from '../../../../shared/models/UserModel/AdminUserDto';
import { CommonModule } from '@angular/common';
import {ChangeRoleDto} from '../../../../shared/models/UserModel/ChangeRoleDto';

@Component({
  selector: 'app-users-list',
  imports: [
    ButtonComponent,
    CommonModule
  ],
  templateUrl: './users-list-page.component.html',
  styleUrl: './users-list-page.component.css',
})
export class UsersListPageComponent implements  OnInit{
  public users: AdminUserDto[] = [];
  public roles: string[]= ['ADMIN','USER'];


  constructor(private adminsServices: AdminsServices, private cdr: ChangeDetectorRef) {}


  ngOnInit() {
    this.getAllUsers();
  }

  /**
   * Get all the user of the website
   *
   * @modifies users
   * @effect : Add all the users of the website in users, or bad request
   * */
  private getAllUsers(){
    this.adminsServices.getAllUsers().subscribe({
        next: (userList) => {
          console.log(userList);
          this.users=userList;
          this.cdr.detectChanges();
        },
        error: (err) => {
        }
      });
  }

  /**
   * Ban the user which username is name, or bad request
   *
   * @param name the username of the user you want to ban
   */
  public banUser(name:string){
    let banDto:BanDto={
      username:name,
      reason:'',
    };

    this.adminsServices.banUser(banDto).subscribe({
      next: response => {
        console.log('Utilisateur banni avec succès', response);
        this.getAllUsers();
      },
      error: (err) => {
        console.error('Erreur lors du bannissement', err);
      }
    });

  }

  /**
   * Unban the user which username is name, or bad request
   *
   * @param name the username of the user you want to unban
   */
  public unbanUser(name:string){

    this.adminsServices.unbanUser(name).subscribe({
      next: response => {
        console.log('Utilisateur débanni avec succès', response);
        this.getAllUsers();
      },
      error: (err) => {
        console.error('Erreur lors du débannissement', err);
      }
    });
  }

  /**
   * Change the role of the user which username is name
   *
   * @param name the username of which you want to change the role
   * @param event the event send by the HTML page
   */
  public changeRole(name:string,event:Event){

    const select = event.target as HTMLSelectElement;
    const newRole = select.value;

    let changeRoleDto:ChangeRoleDto={
      username:name,
      role:newRole
    }

    this.adminsServices.changeRole(changeRoleDto).subscribe({
      next: response => {
        console.log('Changement de role réussit', response);
        this.getAllUsers();
      },
      error: (err) => {
        console.error('Erreur lors du changement de role', err);
      }
    });
  }

}
