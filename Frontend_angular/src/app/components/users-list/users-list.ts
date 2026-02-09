import { Component } from '@angular/core';
import {User} from '../Model/UserListModel/User';
import {OnInit} from '@angular/core';
import {UsersListServices} from '../Service/UserListService/users-list-services';


@Component({
  selector: 'app-users-list',
  imports: [],
  templateUrl: './users-list.html',
  styleUrl: './users-list.css',
})
export class UsersList implements  OnInit{

  Users: User[] = [];

  constructor(private UsersListServices: UsersListServices) {}


  ngOnInit() {
    this.getAllUsers();
  }

  private getAllUsers(){
    this.UsersListServices.getAllUsers().subscribe(
      data =>{
        this.Users = data;
      })
  }

}
