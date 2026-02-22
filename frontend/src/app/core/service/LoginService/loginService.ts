import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";
import {User} from '../../../shared/models/UserModel/User';
import {environment} from '../../rest-api-management/environement';
import {API_ENDPOINTS} from '../../rest-api-management/endpoint';

@Injectable({
  providedIn: 'root'
})
export class LoginService{

  private baseUrl= environment.apiBaseUrl;


  constructor(private HttpClient: HttpClient) {}


  /**
   * RegisterPageComponent a new user
   *
   * @param registerDto information of the new user you want to register
   */
  register(registerDto: any): Observable<any> {
    return this.HttpClient.post(`${this.baseUrl}${API_ENDPOINTS.AUTH.REGISTER}`, registerDto,
            { withCredentials: true }
          );
  }

  /**
   * LoginPageComponent the user
   *
   * @param loginDto user login information
   */
  login(loginDto: any): Observable<any> {
    return this.HttpClient.post(`${this.baseUrl}${API_ENDPOINTS.AUTH.LOGIN}`, loginDto,
        { withCredentials: true }
      );
  }

  /**
   * Disconnect connected user
   */
  logout(): Observable<any> {
    return this.HttpClient.post(`${this.baseUrl}${API_ENDPOINTS.AUTH.LOGOUT}`, {},
      { withCredentials: true }
    );
  }

  /**
   * Check wether a user is connected or not
   */
  checkStatus(): Observable<any> {
    return this.HttpClient.get(`${this.baseUrl}${API_ENDPOINTS.AUTH.STATUS}`,
      { withCredentials: true }
    );
  }
}
