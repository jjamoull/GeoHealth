import { Component } from '@angular/core';
import {OnInit} from '@angular/core';
import {UsersServices} from '../../../../core/service/UserService/users-services';
import {UserResponseDto} from '../../../../shared/models/UserModel/UserResponseDto'
import {AdminsServices} from '../../../../core/service/AdminService/admins-services';


@Component({
  selector: 'app-users-list',
  imports: [],
  templateUrl: './users-list-page.component.html',
  styleUrl: './users-list-page.component.css',
})
export class UsersListPageComponent implements  OnInit{
  users: UserResponseDto[] = [];

  constructor(private adminsServices: AdminsServices) {}


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
        this.users = data;
      })
  }

}
