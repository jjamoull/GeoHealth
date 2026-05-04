import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";
import {environment} from '../../../rest-api-management/environment';
import {MessageDto} from '../../../../shared/models/MessageDto';
import { API_ENDPOINTS } from '../../../rest-api-management/endpoint';


@Injectable({
  providedIn: 'root'
})
export class AdminFinalMapService{

  private baseUrl= environment.apiBaseUrl;

  constructor(private HttpClient: HttpClient) {}

  /**
   * Delete the map by giving its id (Only for admin)
   *
   * @param id The id of the map you want to delete
   */
    deleteMap(id: number) : Observable<MessageDto> {
      return this.HttpClient.get<MessageDto>(`${this.baseUrl}${API_ENDPOINTS.ADMIN.MAPS.FINALMAPS.DELETE}/${id}`,
        {withCredentials: true}
      );
    }

  /**
   * Upload a new map (only for admin)
   *
   * @param saveDto information about the map you want to upload
   */
    uploadNewMap(saveDto: any): Observable<any> {
      return this.HttpClient.post(`${this.baseUrl}${API_ENDPOINTS.ADMIN.MAPS.FINALMAPS.UPLOAD}`,
        saveDto,
        { withCredentials: true }
      );
    }

  /**
   * Deletes an existing final map (only for admin)
   *
   * @param finalMapId the id of the map wanting to be deleted
   */
    deleteFinalMap(finalMapId: number): Observable<any> {
      return this.HttpClient.delete(`${this.baseUrl}${API_ENDPOINTS.ADMIN.MAPS.FINALMAPS.DELETE}/${finalMapId}`,
        { withCredentials: true }
      );
    }
}
