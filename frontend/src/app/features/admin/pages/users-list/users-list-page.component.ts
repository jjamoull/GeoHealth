import { Component } from '@angular/core';
import {User} from '../../../../shared/models/UserModel/User';
import {OnInit} from '@angular/core';
import {UsersServices} from '../../../../core/service/UserService/users-services';


@Component({
  selector: 'app-users-list',
  imports: [],
  templateUrl: './users-list-page.component.html',
  styleUrl: './users-list-page.component.css',
})
export class UsersListPageComponent implements  OnInit{
  Users: User[] = [];

  constructor(private UsersListServices: UsersServices) {}


  ngOnInit() {
    this.getAllUsers();
  }

  /**
   * @effect : modifies variable under the name of "Users" with data of all users in database Springboot
   * @return : Retrieves all users in dataBase Springboot at URL : "http://localhost:8080/users"
   * */
  private getAllUsers(){
    this.UsersListServices.getAllUsers().subscribe(
      data =>{
        this.Users = data;
      })
  }

}
