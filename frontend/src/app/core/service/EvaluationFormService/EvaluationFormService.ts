import {Injectable} from '@angular/core';
import {environment} from '../../rest-api-management/environment';
import {HttpClient} from '@angular/common/http';
import {SaveEvaluationFormDto} from '../../../shared/models/EvaluationFormModel/SaveEvaluationFormDto';
import {Observable} from 'rxjs';
import {API_ENDPOINTS} from '../../rest-api-management/endpoint';
import {UpdateEvaluationFormDto} from '../../../shared/models/EvaluationFormModel/UpdateEvaluationFormDto';
import {ResponseEvaluationFormDto} from '../../../shared/models/EvaluationFormModel/ResponseEvaluationFormDto';


@Injectable({
  providedIn: 'root'
})
export class EvaluationFormService {

  private baseUrl= environment.apiBaseUrl;

  constructor(private httpClient: HttpClient) {}

  /**
   * Save a form
   *
   * @param saveEvaluationFormDto the information about the form you want to save
   */
  public saveForm(saveEvaluationFormDto:SaveEvaluationFormDto): Observable<any>{
    return this.httpClient.post(
      `${this.baseUrl}${API_ENDPOINTS.EVALUATIONFORM.SAVE}`,
      saveEvaluationFormDto,
      {withCredentials:true}
    );
  }

  /**
   * Update a from
   *
   * @param updateEvaluationFormDto the information about the evaluation form you want to update
   */
  public updateForm(updateEvaluationFormDto:UpdateEvaluationFormDto):Observable<any>{
    return this.httpClient.put(
      `${this.baseUrl}${API_ENDPOINTS.EVALUATIONFORM.UPDATE}`,
      updateEvaluationFormDto,
      {withCredentials:true}
    );
  }

  /**
   * Get the form of the connected user for a division for a specific map
   *
   * @param finalMapId the id of the map you are interested in
   * @param division the division you are interested in
   *
   * @return the form if it exists, not found if it does not exist, unauthorized otherwise
   */
  public getMyFormForADiv(finalMapId:number, division:String):Observable<ResponseEvaluationFormDto>{
    return this.httpClient.get<ResponseEvaluationFormDto>(
      `${this.baseUrl}${API_ENDPOINTS.EVALUATIONFORM.GETMYFORMFORADIVFORAMAP}/${finalMapId}/${division}`,
      {withCredentials:true}
    );
  }

  /**
   * Get all the form for a specific map
   *
   * @param finalMapId the id of the map you are interested in
   *
   * @return A list of all the forms
   */
  public getAllForm(finalMapId:number):Observable<ResponseEvaluationFormDto[]>{
    return this.httpClient.get<ResponseEvaluationFormDto[]>(
      `${this.baseUrl}${API_ENDPOINTS.EVALUATIONFORM.GETALLFORMFORAMAP}/${finalMapId}`,
      {withCredentials:true}
    );
  }

  /**
   * Delete a form by an id
   *
   * @param id the id of the form to delete
   */
  public deleteForm(id: number): Observable<any> {
    return this.httpClient.delete(`${this.baseUrl}${API_ENDPOINTS.EVALUATIONFORM.DELETEFORM}/${id}`,
      {withCredentials: true}
      );
    }
}
