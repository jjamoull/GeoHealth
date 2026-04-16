import {Injectable} from '@angular/core';
import {environment} from '../../../rest-api-management/environment';
import {HttpClient} from '@angular/common/http';
import {API_ENDPOINTS} from '../../../rest-api-management/endpoint';
import {Observable} from 'rxjs';
import {DivisionRiskDto} from '../../../../shared/models/MeasureModel/DivisionRiskDto';

@Injectable({
  providedIn: 'root'
})

export class ModelEvaluationMeasureService {

  private baseUrl = environment.apiBaseUrl;

  constructor(private httpClient: HttpClient) {}

  /**
   * Return the national model field agreement socre for a map if the map exits, not found otherwise
   *
   * @param mapId the id of the map you are interested in
   * @param divisionRiskDto a map containing the risk level in the model for each map
   */
  public getNationalModelFieldAgreementScore(mapId: number, divisionRiskDto: DivisionRiskDto): Observable<number> {
    return this.httpClient.post<number>(
      `${this.baseUrl}${API_ENDPOINTS.MEASURE.MODELEVALUATION.NATIONALMODELFIELDAGREEMENTSCORE}/${mapId}` ,
      divisionRiskDto,
      { withCredentials: true })
  }


  /**
   * Return the divisional level agreement score for a division of a map if the map exits, not found otherwise
   *
   * @param mapId the id of the map you are interested in
   * @param division the division you are interested in
   * @param divisionRisk the risk level for the division you are interested in
   */
  public getWeightedDivisionalLevelAgreementScore(mapId: number, division: string, divisionRisk: string): Observable<number> {
    return this.httpClient.get<number>(`${this.baseUrl}${API_ENDPOINTS.MEASURE.MODELEVALUATION.WEIGHTEDDIVISIONALLEVELAGREEMENTSCORE}/${mapId}/${division}/${divisionRisk}`,
      {withCredentials: true})
  }


}

