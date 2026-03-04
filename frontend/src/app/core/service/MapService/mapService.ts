import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";
import { environment } from '../../rest-api-management/environment';
import { API_ENDPOINTS } from '../../rest-api-management/endpoint';
import { MapDto } from '../../../shared/models/MapModel/MapDto';
import { MapListDto } from '../../../shared/models/MapModel/MapListDto';
import { MessageDto } from '../../../shared/models/MessageDto';

@Injectable({
  providedIn: 'root'
})
export class MapService{

  private baseUrl= environment.apiBaseUrl;

  constructor(private HttpClient: HttpClient) {}

  getAllMaps(): Observable<MapListDto[]> {
    return this.HttpClient.get<MapListDto[]>(`${this.baseUrl}${API_ENDPOINTS.MAPS.ALLMAPS}`,
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
