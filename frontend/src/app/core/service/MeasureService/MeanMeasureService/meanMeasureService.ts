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

  /**
   * Return the mean agreement score for a division of a map if the map exits, not found otherwise
   *
   * @param mapId the id of the map you are interested in
   * @param division the division you are interested in
   */
  public getMeanDivisionalAgreementScore(mapId: number, division: string): Observable<number> {
    return this.httpClient.get<number>(
      `${this.baseUrl}${API_ENDPOINTS.MEASURE.MEAN.DIVISIONALAGREEMENTSCORE}/${mapId}/${division}`,
      {withCredentials: true})
  }

  /**
   * Return the mean certainty score for a division of a map if the map exits, not found otherwise
   *
   * @param mapId the id of the map you are interested in
   * @param division the division you are interested in
   */
  public getMeanCertaintyForMapForDivision(mapId: number, division: string): Observable<number> {
    return this.httpClient.get<number>(
      `${this.baseUrl}${API_ENDPOINTS.MEASURE.MEAN.CERTAINTY}/${mapId}/${division}`,
      {withCredentials: true})
  }

  /**
   * Return the dominant perceived risk for a division of a map if the map exits, not found otherwise
   *
   * @param mapId the id of the map you are interested in
   * @param division the division you are interested in
   */
  public getDominantPerceivedRiskLevelForMapForDivision(mapId: number, division: string): Observable<string> {
    return this.httpClient.get(
      `${this.baseUrl}${API_ENDPOINTS.MEASURE.MEAN.DOMINANTPERCEIVEDRISKLEVEL}/${mapId}/${division}`,
      {responseType: 'text', withCredentials: true}
    );
  }
}

