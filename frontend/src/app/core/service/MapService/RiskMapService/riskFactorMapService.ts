import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";
import { environment } from '../../../rest-api-management/environment';


@Injectable({
  providedIn: 'root'
})
export class RiskFactorMapService {

  private baseUrl= environment.apiBaseUrl;

  constructor(private HttpClient: HttpClient) {}

  uploadNewRiskFactor(formData: FormData ): Observable<any> {
    return this.HttpClient.post(`${this.baseUrl}${API_ENDPOINTS.RISKFACTORMAP.UPLOAD}`,
      formData,
      { withCredentials: true }
    );
  }


}
