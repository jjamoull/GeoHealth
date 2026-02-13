import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";
import {User} from '../../Model/UserListModel/User';

@Injectable({
  providedIn: 'root'
})
export class LoginService{
  private userListUrl: string= "http://localhost:8080/users";
  private userSaveUrl:string = "http://localhost:8080/auth/register";


  constructor(private HttpClient: HttpClient) {
    this.userListUrl = "http://localhost:8080/users";
    this.userSaveUrl = "http://localhost:8080/users/save";
  }

  register(registerDto: any): Observable<any> {
    return this.HttpClient.post('http://localhost:8080/auth/register', registerDto,
            { withCredentials: true }
          );
  }

  login(loginDto: any): Observable<any> {
    return this.HttpClient.post('http://localhost:8080/auth/login', loginDto,
        { withCredentials: true }
      );
  }

  logout(): Observable<any> {
    return this.HttpClient.post('http://localhost:8080/auth/logout', {},
      { withCredentials: true }
    );
  }

  checkStatus(): Observable<any> {
    return this.HttpClient.get('http://localhost:8080/auth/status',
      { withCredentials: true }
    );
  }
}
