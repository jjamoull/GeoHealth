import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";
import {environment} from '../../../rest-api-management/environment';
import {API_ENDPOINTS} from '../../../rest-api-management/endpoint';
import { RasterMapListDto } from '../../../../shared/models/MapModel/RasterMapModel/RasterMapListDto';

@Injectable({
  providedIn: 'root'
})
export class RasterMapService {

  private baseUrl= environment.apiBaseUrl;

  constructor(private HttpClient: HttpClient) {}

  getAllMapsOfType(rasterType : string): Observable<RasterMapListDto[]> {
    return this.HttpClient.get<RasterMapListDto[]>(`${this.baseUrl}${API_ENDPOINTS.RASTERMAPS.ALLMAPS}/${rasterType}`,
      { withCredentials: true }
    );
  }
}
