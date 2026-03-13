import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";
import {environment} from '../../../rest-api-management/environment';
import {API_ENDPOINTS} from '../../../rest-api-management/endpoint';


@Injectable({
  providedIn: 'root'
})
export class RiskFactorMapService {

  private baseUrl= environment.apiBaseUrl;

  constructor(private HttpClient: HttpClient) {}


}
