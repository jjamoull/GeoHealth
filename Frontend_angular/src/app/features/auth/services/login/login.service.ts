import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";
import {UserModel} from '../../../../shared/models/user-list-model/user.model';

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

  public addUser(user: UserModel): Observable<UserModel> {
    return this.HttpClient.post<UserModel>(this.userSaveUrl, user);
  }



}
