import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class RiskFactorMapService {

  private DBMapUrl = 'http://localhost:8080/riskFactor'

  constructor(private HttpClient: HttpClient) {}

  uploadNewRiskFactor(formData: FormData ): Observable<any> {
    return this.HttpClient.post(`${this.DBMapUrl}/file`,
      formData,
      { withCredentials: true }
    );
  }


}
