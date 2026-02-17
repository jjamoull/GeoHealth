import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";
import {User} from '../../Model/UserModel/User';
import {environment} from '../../../restApiManagement/environement';
import {API_ENDPOINTS} from '../../../restApiManagement/endpoint';

@Injectable({
  providedIn: 'root'
})
export class LoginService{

  private baseUrl= environment.apiBaseUrl;


  constructor(private HttpClient: HttpClient) {}

  register(registerDto: any): Observable<any> {
    return this.HttpClient.post(`${this.baseUrl}${API_ENDPOINTS.AUTH.REGISTER}`, registerDto,
            { withCredentials: true }
          );
  }

  login(loginDto: any): Observable<any> {
    return this.HttpClient.post(`${this.baseUrl}${API_ENDPOINTS.AUTH.LOGIN}`, loginDto,
        { withCredentials: true }
      );
  }

  logout(): Observable<any> {
    return this.HttpClient.post(`${this.baseUrl}${API_ENDPOINTS.AUTH.LOGOUT}`, {},
      { withCredentials: true }
    );
  }

  checkStatus(): Observable<any> {
    return this.HttpClient.get(`${this.baseUrl}${API_ENDPOINTS.AUTH.STATUS}`,
      { withCredentials: true }
    );
  }
}
