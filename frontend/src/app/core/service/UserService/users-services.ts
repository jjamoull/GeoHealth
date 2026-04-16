import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {User} from '../../../shared/models/UserModel/User';
import {BehaviorSubject, Observable} from "rxjs";
import {UserResponseDto} from '../../../shared/models/UserModel/UserResponseDto';
import {UserUpdateDto} from '../../../shared/models/UserModel/UserUpdateDto';
import {UpdatePasswordDto} from '../../../shared/models/UserModel/UpdatePasswordDto';
import {environment} from '../../rest-api-management/environment';
import {API_ENDPOINTS} from '../../rest-api-management/endpoint';
import {DeleteAccountDto} from '../../../shared/models/UserModel/DeleteAccountDto';
import {UserAnnotationDto} from '../../../shared/models/UserModel/UserAnnotationDto';


@Injectable({
  providedIn: 'root'
})
export class UsersServices {

  private baseUrl= environment.apiBaseUrl;



  constructor(private httpClient: HttpClient) {}



  /**
   * @return the currently connected user
   */
  public getConnectedUser(): Observable<UserResponseDto> {
    return this.httpClient.get<UserResponseDto>(`${this.baseUrl}${API_ENDPOINTS.USER.PROFILE}`, { withCredentials: true })
  }

  /**
   * @return the currently connected user with his ID and username for annotations
   */
  public getUserForAnnotation(): Observable<UserAnnotationDto> {
    return this.httpClient.get<UserAnnotationDto>(`${this.baseUrl}${API_ENDPOINTS.USER.ANNOTATIONS}`, { withCredentials: true })
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

  /**
   * Check if a user is an admin or not
   */
  public isAdmin():Observable<boolean>{
    return this.httpClient.get<boolean>(`${this.baseUrl}${API_ENDPOINTS.USER.ISADMIN}`,{withCredentials: true})
  }

}

