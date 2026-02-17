import { Injectable } from '@angular/core';
import {response} from 'express';
import {catchError, map, Observable, of} from 'rxjs';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {environment} from '../../restApiManagement/environement';
import {API_ENDPOINTS} from '../../restApiManagement/endpoint';


@Injectable({
  providedIn: 'root',
})
export class AuthService {



  private baseUrl:string = environment.apiBaseUrl


  constructor(private http: HttpClient) {}

  /**
   * Check if the connected user token is valid or not
   *
   * @return true if valid, false otherwise
   */
  isTokenValid(): Observable<boolean> {
    return this.http.get(`${this.baseUrl}${API_ENDPOINTS.AUTH.STATUS}`, { observe: 'response', responseType: 'text', withCredentials: true  }).pipe(
      map(res => {return res.status === 200;}),
      catchError(() => of(false))
    );
  }
}
