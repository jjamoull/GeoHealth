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


  public getNationalModelFieldAgreementScore(mapId: number, divisionRiskDto: DivisionRiskDto): Observable<number> {
    return this.httpClient.post<number>(
      `${this.baseUrl}${API_ENDPOINTS.MEASURE.MODELEVALUATION.NATIONALMODELFIELDAGREEMENTSCORE}/${mapId}` ,
      divisionRiskDto,
      { withCredentials: true })
  }


  public getWeightedDivisionalLevelAgreementScore(mapId: number, division: string, divisionRisk: string): Observable<number> {
    return this.httpClient.get<number>(`${this.baseUrl}${API_ENDPOINTS.MEASURE.MODELEVALUATION.WEIGHTEDDIVISIONALLEVELAGREEMENTSCORE}/${mapId}/${division}/${divisionRisk}`,
      {withCredentials: true})
  }


}

