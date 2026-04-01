import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {BehaviorSubject, Observable} from "rxjs";
import {environment} from '../../rest-api-management/environment';
import {API_ENDPOINTS} from '../../rest-api-management/endpoint';
import {DivisionRiskDto} from '../../../shared/models/MeasureModel/DivisionRiskDto'


@Injectable({
  providedIn: 'root'
})

export class MeasureService {

  private baseUrl = environment.apiBaseUrl;

  constructor(private httpClient: HttpClient) {}

  public getGlobalConsensusIndex(mapId: number, divisionRiskDto: DivisionRiskDto): Observable<number> {
    return this.httpClient.post<number>(`${this.baseUrl}${API_ENDPOINTS.MEASURE.GLOBALCONSENSUSINDEX}/${mapId}` ,
      divisionRiskDto,
      { withCredentials: true })
  }

  public getWeightedEntropy(mapId: number, division: string, divisionRisk: string): Observable<number> {
    return this.httpClient.get<number>(`${this.baseUrl}${API_ENDPOINTS.MEASURE.WEIGHTEDENTROPY}/${mapId}/${division}/${divisionRisk}`,
    {withCredentials: true})
    }

}

