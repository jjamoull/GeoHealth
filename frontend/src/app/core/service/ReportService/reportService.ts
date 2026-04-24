import {Injectable} from '@angular/core';
import {environment} from '../../rest-api-management/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {API_ENDPOINTS} from '../../rest-api-management/endpoint';
import {DivisionRiskDto} from '../../../shared/models/MeasureModel/DivisionRiskDto';


@Injectable({
  providedIn: 'root'
})

export class ReportService{

  private baseUrl = environment.apiBaseUrl;

  constructor(private httpClient: HttpClient) {}

  public getReport(mapId: number, divisionRiskDto: DivisionRiskDto): Observable<any> {
    return this.httpClient.post(
      `${this.baseUrl}${API_ENDPOINTS.REPORT.GET}/${mapId}` ,
      divisionRiskDto,
      { withCredentials: true,
        responseType:'blob'})
  }
}
