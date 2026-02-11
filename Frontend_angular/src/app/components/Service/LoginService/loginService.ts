import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";
import {User} from '../../Model/UserListModel/User';


@Injectable({
  providedIn: 'root'
})
export class LoginService{
  private userListUrl: string= "http://localhost:8080/users";
  private userSaveUrl:string = "http://localhost:8080/users/save";


  constructor(private HttpClient: HttpClient) {
    this.userListUrl = "http://localhost:8080/users";
    this.userSaveUrl = "http://localhost:8080/users/save";
  }

  public addUser(user: User): Observable<User> {
    return this.HttpClient.post<User>(this.userSaveUrl, user);
  }



}
