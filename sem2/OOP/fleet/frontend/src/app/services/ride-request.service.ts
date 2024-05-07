import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Environment } from '../enviroment';
import { ApiService } from '../utils/url.composer';
import {RideRequest} from "../models/ride-request";

@Injectable({
  providedIn: 'root'
})
export class RideRequestService {
  private endpointUrl = ApiService.getEndpointUrl(Environment.getInstance().getEndpoint('rideRequest')) ; // Update the URL

  constructor(private http: HttpClient) { }

  getRideRequest(id: number): Observable<any> {
    return this.http.get(ApiService.createUrl(this.endpointUrl, { id }));
  }

  createRideRequest(rideRequest: RideRequest): Observable<any> {
    console.log(rideRequest);
    console.log(ApiService.createUrl(this.endpointUrl), { rideRequest })
    return this.http.post<RideRequest>("http://localhost:8080/ride-request", rideRequest);
  }

  updateRideRequest(rideRequest: RideRequest): Observable<any> {
    return this.http.put(ApiService.createUrl(this.endpointUrl), rideRequest);
  }

  deleteRideRequest(id: number): Observable<any> {
    return this.http.delete(ApiService.createUrl(this.endpointUrl, { id }));
  }
}

