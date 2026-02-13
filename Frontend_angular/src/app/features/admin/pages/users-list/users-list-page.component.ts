import { Component } from '@angular/core';
import {UserModel} from '../../../../shared/models/user-list-model/user.model';
import {OnInit} from '@angular/core';
import {UsersListService} from '../../../../shared/services/user-list-service/users-list.service';


@Component({
  selector: 'app-users-list',
  imports: [],
  templateUrl: './users-list-page.component.html',
  styleUrl: './users-list-page.component.css',
})
export class UsersListPageComponent implements  OnInit{
  Users: UserModel[] = [];

  constructor(private UsersListServices: UsersListService) {}


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
