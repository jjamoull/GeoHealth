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
   * Get the form which has the specify id
   *
   * @param id the id of the form you want to get
   * @return the form if it exists, not found otherwise
   */
  public getFormById(id:number):Observable<ResponseValidationFormDto>{
    return this.httpClient.get<ResponseValidationFormDto>(
      `${this.baseUrl}${API_ENDPOINTS.VAlIDATIONFORM.GETFORM}/${id}`,
      {withCredentials:true}
    );
  }

  /**
   * Get the form of the connected user for a department
   *
   * @param department the department you are interested in
   * @return the form if it exists, not found if it does not exist, unauthorized otherwise
   */
  public getMyFormForADep(department:String):Observable<ResponseValidationFormDto>{
    return this.httpClient.get<ResponseValidationFormDto>(
      `${this.baseUrl}${API_ENDPOINTS.VAlIDATIONFORM.GETMYFORMFORADEP}/${department}`,
      {withCredentials:true}
    );
  }

  /**
   * Get all the form
   *
   * @return A list of all the forms
   */
  public getAllForm():Observable<ResponseValidationFormDto[]>{
    return this.httpClient.get<ResponseValidationFormDto[]>(
      `${this.baseUrl}${API_ENDPOINTS.VAlIDATIONFORM.GETALLFORM}`,
      {withCredentials:true}
    );
  }

  /**
   * Get all the form for a department
   *
   * @return A list of all the forms for a department
   */
  public getAllFormForADep(department:String):Observable<ResponseValidationFormDto[]> {
    return this.httpClient.get<ResponseValidationFormDto[]>(
      `${this.baseUrl}${API_ENDPOINTS.VAlIDATIONFORM.GETALLFORMFORADEP}`,
      {withCredentials: true}
    );
  }

}
