import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";
import { environment } from '../../rest-api-management/environment';
import { API_ENDPOINTS } from '../../rest-api-management/endpoint';
import { MessageDto } from '../../../shared/models/MessageDto';

@Injectable({
  providedIn: 'root'
})
export class AdminMapService{

  private baseUrl= environment.apiBaseUrl;

  constructor(private HttpClient: HttpClient) {}


  deleteMap(id: number) : Observable<MessageDto> {
    return this.HttpClient.get<MessageDto>(`${this.baseUrl}${API_ENDPOINTS.ADMIN.MAPS.DELETE}/${id}`,
      {withCredentials: true}
    );
  }


  uploadNewMap(saveDto: any): Observable<any> {
    console.log("here eyyyyyy")
    return this.HttpClient.post(`${this.baseUrl}${API_ENDPOINTS.ADMIN.MAPS.UPLOAD}`,
      saveDto,
      { withCredentials: true }
    );
  }

}
