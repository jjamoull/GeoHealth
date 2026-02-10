import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";
import {User} from '../../Model/UserListModel/User';


@Injectable({
  providedIn: 'root'
})
export class UsersListServices{
  private userListUrl: string= "http://localhost:8080/users";


  constructor(private HttpClient: HttpClient) {
    this.userListUrl = "http://localhost:8080/users";
  }

  public addUser(User: User):void{
    this.HttpClient.post(`${this.userListUrl}/save`, User);
  }




}
