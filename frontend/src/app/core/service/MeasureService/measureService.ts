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

  /**
   * Return the Global Consenus Index (Mean of the weighted entropies) for a specific map
   *
   * @param mapId the id of the map you are interested in
   * @param divisionRiskDto information about the risk evaluated in the map from which you want the Global Consenus Index
   */
  public getGlobalConsensusIndex(mapId: number, divisionRiskDto: DivisionRiskDto): Observable<number> {
    return this.httpClient.post<number>(`${this.baseUrl}${API_ENDPOINTS.MEASURE.GLOBALCONSENSUSINDEX}/${mapId}` ,
      divisionRiskDto,
      { withCredentials: true })
  }

  /**
   * Return the weighted entropy for a specific division in a specific map
   *
   * @param mapId the id of the map you are interested in
   * @param division the name of the division you are interested in
   * @param divisionRisk the risk evaluated in the map for the division 
   */
  public getWeightedEntropy(mapId: number, division: string, divisionRisk: string): Observable<number> {
    return this.httpClient.get<number>(`${this.baseUrl}${API_ENDPOINTS.MEASURE.WEIGHTEDENTROPY}/${mapId}/${division}/${divisionRisk}`,
    {withCredentials: true})
    }

  /**
   * Return the krippendorff's alpha measure for a specific map
   *
   * @param mapId the id of the map you are interested in
   */
  public  getKrippensdorff(mapId: number): Observable<number> {
    return this.httpClient.get<number>(`${this.baseUrl}${API_ENDPOINTS.MEASURE.KRIPPENSDORFF}/${mapId}`,
      {withCredentials: true})
  }

}

