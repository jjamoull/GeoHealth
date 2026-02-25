import { Injectable } from '@angular/core';
import {catchError, map, Observable, of} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../rest-api-management/environment';
import {API_ENDPOINTS} from '../../rest-api-management/endpoint';
import {BanDto} from '../../../shared/models/UserModel/BanDto';
import {ChangeRoleDto} from '../../../shared/models/UserModel/ChangeRoleDto';
import {AdminUserDto} from '../../../shared/models/UserModel/AdminUserDto';


@Injectable({
  providedIn: 'root',
})
export class AdminsServices {



  private baseUrl:string = environment.apiBaseUrl


  constructor(private http: HttpClient) {}

  public getAllUsers(): Observable<AdminUserDto[]> {
    return this.http.get<AdminUserDto[]>(`${this.baseUrl}${API_ENDPOINTS.ADMIN.ALLUSER}`,
      { withCredentials: true }
    );

  }

  public banUser(banDto:BanDto):Observable<any>{
    return this.http.put(`${this.baseUrl}${API_ENDPOINTS.ADMIN.BAN}`,banDto,
      { withCredentials: true }
    );
  }

  public unbanUser(username:string):Observable<any>{
    return this.http.put(`${this.baseUrl}${API_ENDPOINTS.ADMIN.UNBAN}${username}`,
      { withCredentials: true }
    );
  }

  public changeRole(changeRoleDto: ChangeRoleDto):Observable<any>{
    return this.http.put(`${this.baseUrl}${API_ENDPOINTS.ADMIN.CHANGEROLE}`,changeRoleDto,
      { withCredentials: true }
    );
  }
}
