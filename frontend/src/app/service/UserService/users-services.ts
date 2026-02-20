import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {User} from '../../model/UserModel/User';
import {Observable} from "rxjs";
import {UserResponseDto} from '../../model/UserModel/UserResponseDto';
import {UserUpdateDto} from '../../model/UserModel/UserUpdateDto';
import {UpdatePasswordDto} from '../../model/UserModel/UpdatePasswordDto';
import {environment} from '../../restApiManagement/environement';
import {API_ENDPOINTS} from '../../restApiManagement/endpoint';
import {DeleteAccountDto} from '../../model/UserModel/DeleteAccountDto';


@Injectable({
  providedIn: 'root'
})
export class UsersServices {

  private baseUrl= environment.apiBaseUrl;

  constructor(private httpClient: HttpClient) {}

  /**
   * @return : Retrieves all users in dataBase
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
   */
  public updateUser(userUpdateDto:UserUpdateDto):Observable<any>{
    return this.httpClient.put(`${this.baseUrl}${API_ENDPOINTS.USER.UPDATE}`,userUpdateDto,{ withCredentials: true })
  }

  /**
   * Update the currently connected user password information
   *
   * @param userPasswordDto new password information
   */
  public changePassword(userPasswordDto: UpdatePasswordDto): Observable<any>{
    return this.httpClient.put(`${this.baseUrl}${API_ENDPOINTS.USER.CHANGEPASSWORD}`,userPasswordDto, {withCredentials: true})
  }

  /**
   * Delete the account selected
   *
   * @param deleteAccountDto the information on the account you want to delete
   */
  public deleteUserAccount(deleteAccountDto:DeleteAccountDto):Observable<any>{
    return this.httpClient.delete(`${this.baseUrl}${API_ENDPOINTS.USER.DELETE}`,{body:deleteAccountDto, withCredentials: true})
  }

}

