import {Injectable, signal} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {catchError, map, Observable, of, tap} from "rxjs";
import {User} from '../../../shared/models/UserModel/User';
import {environment} from '../../rest-api-management/environment';
import {API_ENDPOINTS} from '../../rest-api-management/endpoint';

@Injectable({
  providedIn: 'root'
})
export class LoginService{

  private baseUrl= environment.apiBaseUrl;

  private LoggedIn= false;


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
    return this.HttpClient.get(`${this.baseUrl}${API_ENDPOINTS.AUTH.STATUS}`,{ withCredentials: true })
  }

  /**
   * Track whether a user is logged in or no
   *
   * @return true if logging, false otherwise
   */
  public isLoggedIn():boolean{
    return this.LoggedIn
  }

  /**
   * Change the LoggedIn value
   *
   * @param value the new loggedIn value
   */
  public setLoggedIn(value:boolean):void{
    this.LoggedIn=value;
  }


}
