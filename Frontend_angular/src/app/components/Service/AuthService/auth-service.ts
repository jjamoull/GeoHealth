import { Injectable } from '@angular/core';
import {response} from 'express';
import {catchError, map, Observable, of} from 'rxjs';
import {HttpClient, HttpResponse} from '@angular/common/http';


@Injectable({
  providedIn: 'root',
})
export class AuthService {



  private tokenValidationUrl:string = "http://localhost:8080/auth/status"


  constructor(private http: HttpClient) {}

  isTokenValid(): Observable<boolean> {
    return this.http.get(this.tokenValidationUrl, { observe: 'response', responseType: 'text', withCredentials: true  }).pipe(
      map(res => {return res.status === 200;}),
      catchError(() => of(false))
    );
  }
}
