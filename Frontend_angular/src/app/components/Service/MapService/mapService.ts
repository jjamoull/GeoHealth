import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class MapService{


  constructor(private HttpClient: HttpClient) {
  }

  save(saveDto: any): Observable<any> {
    return this.HttpClient.post('http://localhost:8080/maps/uploadShapeFile', saveDto,
      { withCredentials: true }
    );
  }


}
