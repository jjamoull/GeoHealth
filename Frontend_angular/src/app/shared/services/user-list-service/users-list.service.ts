import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";
import {UserModel} from '../../models/user-list-model/user.model';


@Injectable({
  providedIn: 'root'
})
export class UsersListService {
  private userListUrl: string= "http://localhost:8080/users";


  constructor(private HttpClient: HttpClient) {
    this.userListUrl = "http://localhost:8080/users";
  }

  /**
   * @return : Retrieves all users in dataBase Springboot at URL : "http://localhost:8080/users"
   * */
  public getAllUsers():Observable<UserModel[]>{
    return this.HttpClient.get<UserModel[]>(`${this.userListUrl}/all`);
  }


  /**
   * @return user in database from their username
   * */
  public getUserByUsername(username : string):Observable<UserModel>{
    return this.HttpClient.get<UserModel>(`${this.userListUrl}/${username}`);
  }




}

