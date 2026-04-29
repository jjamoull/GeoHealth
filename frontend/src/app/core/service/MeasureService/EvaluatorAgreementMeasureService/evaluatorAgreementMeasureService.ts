import {Injectable} from '@angular/core';
import {environment} from '../../../rest-api-management/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {API_ENDPOINTS} from '../../../rest-api-management/endpoint';


@Injectable({
  providedIn: 'root'
})

export class EvaluatorAgreementMeasureService{

  private baseUrl = environment.apiBaseUrl;

  constructor(private httpClient: HttpClient) {}

  /**
   *  return the consensus score for a division if the map exists, not found otherwise
   *
   * @param mapId the map you are interested in
   * @param division the division you are interested in
   */
  public getDivisionalConsensusScore(mapId: number, division: string): Observable<number> {
    return this.httpClient.get<number>(
      `${this.baseUrl}${API_ENDPOINTS.MEASURE.EVAlUATORAGREEMENT.DIVISIONALCONSENSUSSCORE}/${mapId}/${division}`,
      {withCredentials: true})
  }

  /**
   *  Return the national consensus score for a map if the map exists, not found otherwise
   *
   * @param mapId the id of the map you are interested in
   */
  public getNationalConsensusScore(mapId: number): Observable<number> {
    return this.httpClient.get<number>(`${this.baseUrl}${API_ENDPOINTS.MEASURE.EVAlUATORAGREEMENT.NATIONALCONSENSUSSCORE}/${mapId}`,
      {withCredentials: true})
  }

  /**
   * Return the krippendorff's alpha measure for a specific map
   *
   * @param mapId the id of the map you are interested in
   */
  public getKrippendorff(mapId: number): Observable<number> {
    return this.httpClient.get<number>(`${this.baseUrl}${API_ENDPOINTS.MEASURE.EVAlUATORAGREEMENT.KRIPPENDORFFALPHA}/${mapId}`,
      {withCredentials: true})
  }

}

