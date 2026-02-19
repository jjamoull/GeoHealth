import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class MapService{

  private DBMapUrl = 'http://localhost:8080'

  constructor(private HttpClient: HttpClient) {}

  uploadNewMap(saveDto: any): Observable<any> {
    return this.HttpClient.post(`${this.DBMapUrl}/maps/uploadShapeFile`,
      saveDto,
      { withCredentials: true }
    );
  }


}
