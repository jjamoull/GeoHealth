import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {catchError, Observable, of} from "rxjs";
import {User} from '../../Model/UserModel/User';
import {UserResponseDto} from '../../Model/UserModel/UserResponseDto';
import {UserUpdateDto} from '../../Model/UserModel/UserUpdateDto';
import {environment} from '../../../restApiManagement/environement';
import {API_ENDPOINTS} from '../../../restApiManagement/endpoint';


@Injectable({
  providedIn: 'root'
})
export class UsersServices {

  private baseUrl= environment.apiBaseUrl;

  constructor(private httpClient: HttpClient) {}

  /**
   * @return : Retrieves all users in dataBase Springboot at URL : "http://localhost:8080/users"
   * */
  public getAllUsers():Observable<User[]>{
    return this.httpClient.get<User[]>(`${this.baseUrl}${API_ENDPOINTS.USER.UPDATE}`,{ withCredentials: true });
  }


  /**
   * @return the currently connected user
   */
  public getConnectedUser(): Observable<UserResponseDto> {
    return this.httpClient.get<UserResponseDto>(`${this.baseUrl}${API_ENDPOINTS.USER.PROFILE}`, { withCredentials: true })
  }

  /**
   * Update the currently connected user information
   *
   * @param userUpdateDto new user information
   * @return https response
   */
  public updateUser(userUpdateDto:UserUpdateDto):Observable<any>{
    return this.httpClient.put(`${this.baseUrl}${API_ENDPOINTS.USER.UPDATE}`,userUpdateDto,{ withCredentials: true })
  }



}

