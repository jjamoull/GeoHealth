import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";
import { environment } from '../../../rest-api-management/environment';
import { API_ENDPOINTS } from '../../../rest-api-management/endpoint';
import { FinalMapDto } from '../../../../shared/models/MapModel/FinalMapModel/FinalMapDto';
import { FinalMapListDto } from '../../../../shared/models/MapModel/FinalMapModel/FinalMapListDto';
import { MessageDto } from '../../../../shared/models/MessageDto';

@Injectable({
  providedIn: 'root'
})
export class FinalMapService {

  private baseUrl= environment.apiBaseUrl;

  constructor(private HttpClient: HttpClient) {}

  getAllMaps(): Observable<FinalMapListDto[]> {
    return this.HttpClient.get<FinalMapListDto[]>(`${this.baseUrl}${API_ENDPOINTS.MAPS.ALLMAPS}`,
      {withCredentials: true}
      );
    }

  deleteMap(id: number) : Observable<MessageDto> {
    return this.HttpClient.get<MessageDto>(`${this.baseUrl}${API_ENDPOINTS.MAPS.DELETE}/${id}`,
      {withCredentials: true}
      );
    }

  getMap(id: number): Observable<any> {
      return this.HttpClient.get(`${this.baseUrl}${API_ENDPOINTS.MAPS.GET}/${id}`,
        { withCredentials: true }
      );
    }

  uploadNewMap(saveDto: any): Observable<any> {
    return this.HttpClient.post(`${this.baseUrl}${API_ENDPOINTS.MAPS.UPLOAD}`,
      saveDto,
      { withCredentials: true }
    );
  }

}
