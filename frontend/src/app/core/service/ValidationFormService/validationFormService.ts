import {Injectable} from '@angular/core';
import {environment} from '../../rest-api-management/environment';
import {HttpClient} from '@angular/common/http';
import {SaveValidationFormDto} from '../../../shared/models/ValidationFormModel/SaveValidationFormDto';
import {Observable} from 'rxjs';
import {API_ENDPOINTS} from '../../rest-api-management/endpoint';
import {UpdateValidationFormDto} from '../../../shared/models/ValidationFormModel/UpdateValidationFormDto';
import {ResponseValidationFormDto} from '../../../shared/models/ValidationFormModel/ResponseValidationFormDto';


@Injectable({
  providedIn: 'root'
})
export class ValidationFormService{

  private baseUrl= environment.apiBaseUrl;

  constructor(private httpClient: HttpClient) {}

  /**
   * Save a form
   *
   * @param saveValidationFormDto the information about the validation form you want to save
   */
  public saveForm(saveValidationFormDto:SaveValidationFormDto): Observable<any>{
    return this.httpClient.post(
      `${this.baseUrl}${API_ENDPOINTS.VAlIDATIONFORM.SAVE}`,
      saveValidationFormDto,
      {withCredentials:true}
    );
  }

  /**
   * Update a from
   *
   * @param updateValidationFormDto the information about the validation form you want to update
   */
  public updateForm(updateValidationFormDto:UpdateValidationFormDto):Observable<any>{
    return this.httpClient.put(
      `${this.baseUrl}${API_ENDPOINTS.VAlIDATIONFORM.UPDATE}`,
      updateValidationFormDto,
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
  public getMyFormForADiv(finalMapId:number, division:String):Observable<ResponseValidationFormDto>{
    return this.httpClient.get<ResponseValidationFormDto>(
      `${this.baseUrl}${API_ENDPOINTS.VAlIDATIONFORM.GETMYFORMFORADIVFORAMAP}/${finalMapId}/${division}`,
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
  public getAllForm(finalMapId:number):Observable<ResponseValidationFormDto[]>{
    return this.httpClient.get<ResponseValidationFormDto[]>(
      `${this.baseUrl}${API_ENDPOINTS.VAlIDATIONFORM.GETALLFORMFORAMAP}/${finalMapId}`,
      {withCredentials:true}
    );
  }


}
