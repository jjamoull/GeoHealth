import {Injectable} from '@angular/core';
import {environment} from '../../../rest-api-management/environment';
import {HttpClient} from '@angular/common/http';
import {API_ENDPOINTS} from '../../../rest-api-management/endpoint';
import {Observable} from 'rxjs';


@Injectable({
  providedIn: 'root'
})

export class MeanMeasureService {

  private baseUrl = environment.apiBaseUrl;

  constructor(private httpClient: HttpClient) {}


  public getMeanDivisionalAgreementScore(mapId: number, division: string): Observable<number> {
    return this.httpClient.get<number>(
      `${this.baseUrl}${API_ENDPOINTS.MEASURE.MEAN.DIVISIONALAGREEMENTSCORE}/${mapId}/${division}`,
      {withCredentials: true})
  }

  public getMeanCertaintyForMapForDivision(mapId: number, division: string): Observable<number> {
    return this.httpClient.get<number>(
      `${this.baseUrl}${API_ENDPOINTS.MEASURE.MEAN.CERTAINTY}/${mapId}/${division}`,
      {withCredentials: true})
  }

  public getDominantPerceivedRiskLevelForMapForDivision(mapId: number, division: string): Observable<number> {
    return this.httpClient.get<number>(
      `${this.baseUrl}${API_ENDPOINTS.MEASURE.MEAN.DOMINANTPERCEIVEDRISKLEVEL}/${mapId}/${division}`,
      {withCredentials: true})
  }

}

