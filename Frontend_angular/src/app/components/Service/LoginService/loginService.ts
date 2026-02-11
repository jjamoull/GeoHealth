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
    const token = localStorage.getItem('token');
    const headers = token ? { 'Authorization': `Bearer ${token}` } : undefined;
    return this.HttpClient.post('http://localhost:8080/auth/register', registerDto, { headers });
  }

  login(loginDto: any): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = token ? { 'Authorization': `Bearer ${token}` } : undefined;
    return this.HttpClient.post('http://localhost:8080/auth/login', loginDto, { headers });
  }
}
