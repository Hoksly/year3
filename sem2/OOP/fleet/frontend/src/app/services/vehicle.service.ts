import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Environment } from '../enviroment';
import { ApiService } from '../utils/url.composer';

@Injectable({
  providedIn: 'root'
})
export class VehicleService {
  private endpointUrl = ApiService.getEndpointUrl(Environment.getInstance().getEndpoint('vehicles')) ; // Update the URL

  constructor(private http: HttpClient) { }

  getVehicles(id: number): Observable<any> {
    return this.http.get(ApiService.createUrl(this.endpointUrl, { id }));
  }

  createVehicle(vehicle: any): Observable<any> {
    return this.http.post(ApiService.createUrl(this.endpointUrl), vehicle);
  }

  updateVehicle(vehicle: any): Observable<any> {
    return this.http.put(ApiService.createUrl(this.endpointUrl), vehicle);
  }

  deleteVehicle(id: number): Observable<any> {
    return this.http.delete(ApiService.createUrl(this.endpointUrl, { id }));
  }
}

