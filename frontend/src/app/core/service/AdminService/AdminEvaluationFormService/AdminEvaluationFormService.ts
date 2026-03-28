import {Injectable} from '@angular/core';
import {environment} from '../../../rest-api-management/environment';
import {HttpClient} from '@angular/common/http';
import {API_ENDPOINTS} from '../../../rest-api-management/endpoint';
import {Observable} from 'rxjs';
import {AdminResponseEvaluationFormDto} from '../../../../shared/models/AdminModel/EvaluationFormModel/AdminResponseEvaluationFormDto';

@Injectable({
  providedIn: 'root'
})
export class AdminEvaluationFormService {

  private baseUrl = environment.apiBaseUrl;

  constructor(private httpClient: HttpClient) {}

  /**
   * Get all the form for a specific map
   *
   * @param finalMapId the id of the map you are interested in
   *
   * @return A list of all the forms
   */
  public getAllForm(finalMapId:number):Observable<AdminResponseEvaluationFormDto[]>{
    return this.httpClient.get<AdminResponseEvaluationFormDto[]>(
      `${this.baseUrl}${API_ENDPOINTS.ADMIN.EVALUATIONFORM.GETALLFORMFORAMAP}/${finalMapId}`,
      {withCredentials:true}
    );
  }
}
