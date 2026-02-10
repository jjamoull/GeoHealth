import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";
import {User} from '../../Model/UserListModel/User';


@Injectable({
  providedIn: 'root'
})
export class UsersListServices{
  private UserListURL: string= "http://localhost:8080/users";


  constructor(private HttpClient: HttpClient) {
    this.UserListURL = "http://localhost:8080/users";
  }

  /**
   * @return : Retrieves all users in dataBase Springboot at URL : "http://localhost:8080/users"
   * */
  public getAllUsers():Observable<User[]>{
    return this.HttpClient.get<User[]>(`${this.UserListURL}/all`);
  }


  /**
   * @return user in database from their username
   * */
  public getUserByUsername(username : string):Observable<User>{
    return this.HttpClient.get<User>(`${this.UserListURL}/${username}`);
  }




}

