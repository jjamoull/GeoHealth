import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";
import { environment } from '../../../rest-api-management/environment';
import { API_ENDPOINTS } from '../../../rest-api-management/endpoint';
import { FinalMapListDto } from '../../../../shared/models/MapModel/FinalMapModel/FinalMapListDto';

@Injectable({
  providedIn: 'root'
})
export class FinalMapService {

  private baseUrl= environment.apiBaseUrl;

  constructor(private HttpClient: HttpClient) {}

  getAllMaps(): Observable<FinalMapListDto[]> {
    return this.HttpClient.get<FinalMapListDto[]>(`${this.baseUrl}${API_ENDPOINTS.FINALMAPS.ALLMAPS}`,
      {withCredentials: true}
      );
    }


  getMap(id: number): Observable<any> {
      return this.HttpClient.get(`${this.baseUrl}${API_ENDPOINTS.FINALMAPS.GET}/${id}`,
        { withCredentials: true }
      );
    }

}
