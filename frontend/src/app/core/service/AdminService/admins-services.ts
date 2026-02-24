import { Injectable } from '@angular/core';
import {catchError, map, Observable, of} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../rest-api-management/environment';
import {API_ENDPOINTS} from '../../rest-api-management/endpoint';
import {UserResponseDto} from '../../../shared/models/UserModel/UserResponseDto';


@Injectable({
  providedIn: 'root',
})
export class AdminsServices {



  private baseUrl:string = environment.apiBaseUrl


  constructor(private http: HttpClient) {}

  public getAllUsers(): Observable<UserResponseDto[]> {
    return this.http.get<UserResponseDto[]>(`${this.baseUrl}${API_ENDPOINTS.ADMIN.ALLUSER}`,
      { withCredentials: true }
    );

  }
}
