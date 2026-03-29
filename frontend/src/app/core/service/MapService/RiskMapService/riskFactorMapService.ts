import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";
import {environment} from '../../../rest-api-management/environment';
import {API_ENDPOINTS} from '../../../rest-api-management/endpoint';
import { RiskFactorMapListDto } from '../../../../shared/models/MapModel/RiskFactorMapModel/RiskFactorMapListDto';
import {AdminUserDto} from '../../../../shared/models/AdminModel/UserModel/AdminUserDto';

@Injectable({
  providedIn: 'root'
})
export class RiskFactorMapService {

  private baseUrl= environment.apiBaseUrl;

  constructor(private HttpClient: HttpClient) {}

  getAllMaps(): Observable<RiskFactorMapListDto[]> {
    return this.HttpClient.get<RiskFactorMapListDto[]>(`${this.baseUrl}${API_ENDPOINTS.RISKFACTORMAPS.ALLMAPS}`,
      { withCredentials: true }
    );
  }
}
