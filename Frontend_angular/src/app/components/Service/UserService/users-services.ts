import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {catchError, Observable, of} from "rxjs";
import {User} from '../../Model/UserModel/User';
import {UserResponseDto} from '../../Model/UserModel/UserResponseDto';
import {UserUpdateDto} from '../../Model/UserModel/UserUpdateDto';


@Injectable({
  providedIn: 'root'
})
export class UsersServices {
  private userListUrl: string= "http://localhost:8080/users/all";
  private getConnectedUserUrl: string = "http://localhost:8080/users/profile";
  private userUpdateUrl: string = "http://localhost:8080/users/update";


  constructor(private httpClient: HttpClient) {}

  /**
   * @return : Retrieves all users in dataBase Springboot at URL : "http://localhost:8080/users"
   * */
  public getAllUsers():Observable<User[]>{
    return this.httpClient.get<User[]>(`${this.userListUrl}/all`);
  }


  /**
   * @return user in database from their username
   * */
  public getUserByUsername(username : string):Observable<User>{
    return this.httpClient.get<User>(`${this.userListUrl}/${username}`);
  }

  public getConnectedUser(): Observable<UserResponseDto> {
    return this.httpClient.get<UserResponseDto>(this.getConnectedUserUrl, { withCredentials: true })
  }

  public updateUser(userUpdateDto:UserUpdateDto):Observable<any>{
    console.log('In frontend user update');
    return this.httpClient.put(this.userUpdateUrl,userUpdateDto,{ withCredentials: true })
  }


}

