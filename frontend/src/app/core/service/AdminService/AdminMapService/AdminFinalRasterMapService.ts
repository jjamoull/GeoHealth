import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";
import {environment} from '../../../rest-api-management/environment';
import { API_ENDPOINTS } from '../../../rest-api-management/endpoint';


@Injectable({
  providedIn: 'root'
})
export class AdminFinalRasterMapService{

  private baseUrl= environment.apiBaseUrl;

  constructor(private HttpClient: HttpClient) {}

  /**
   * Upload a new final raster map (only for admin)
   *
   * @param formData information about the risk factor map you want to upload
   */
  uploadNewFinalRasterMap(formData: FormData ): Observable<any> {
    return this.HttpClient.post(`${this.baseUrl}${API_ENDPOINTS.ADMIN.MAPS.FINALRASTERMAPS.UPLOAD}`,
      formData,
      { withCredentials: true }
    );
  }


}
