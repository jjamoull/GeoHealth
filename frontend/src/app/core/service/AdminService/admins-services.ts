import { Injectable } from '@angular/core';
import {catchError, map, Observable, of} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../rest-api-management/environment';
import {API_ENDPOINTS} from '../../rest-api-management/endpoint';
import {BanDto} from '../../../shared/models/UserModel/BanDto';
import {ChangeRoleDto} from '../../../shared/models/UserModel/ChangeRoleDto';
import {AdminUserDto} from '../../../shared/models/UserModel/AdminUserDto';
import {UsersServices} from '../UserService/users-services';
import {subscribe} from 'node:diagnostics_channel';


@Injectable({
  providedIn: 'root',
})
export class AdminsServices {



  private baseUrl:string = environment.apiBaseUrl

  constructor(private http: HttpClient) {}

  /**
   * Get a list of all the users
   *
   * @return A list of all the user (as AdminUserDto) or an error response
   */
  public getAllUsers(): Observable<AdminUserDto[]> {
    return this.http.get<AdminUserDto[]>(`${this.baseUrl}${API_ENDPOINTS.ADMIN.ALLUSER}`,
      { withCredentials: true }
    );

  }

  /**
   * Ban a user
   *
   * @param banDto the information about the user you want to ban
   */
  public banUser(banDto:BanDto):Observable<any>{
    return this.http.put(`${this.baseUrl}${API_ENDPOINTS.ADMIN.BAN}`,banDto,
      { withCredentials: true }
    );
  }

  /**
   * unban a user
   *
   * @param username the username of the user you want to unban
   */
  public unbanUser(username:string):Observable<any>{
    return this.http.put(`${this.baseUrl}${API_ENDPOINTS.ADMIN.UNBAN}${username}`,{},
      { withCredentials: true }
    );
  }

  /**
   * Change the role of a user
   *
   * @param changeRoleDto the information about the user new role
   */
  public changeRole(changeRoleDto: ChangeRoleDto):Observable<any>{
    return this.http.put(`${this.baseUrl}${API_ENDPOINTS.ADMIN.CHANGEROLE}`,changeRoleDto,
      { withCredentials: true }
    );
  }


}
