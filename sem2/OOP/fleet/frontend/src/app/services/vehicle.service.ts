import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VehicleService {
  private url = 'http://localhost:8080/vehicles'; // Update the URL

  constructor(private http: HttpClient) { }

  getVehicles(): Observable<any> {
    return this.http.get(this.url);
  }

  createVehicle(vehicle: any): Observable<any> {
    return this.http.post(this.url, vehicle);
  }

  updateVehicle(vehicle: any): Observable<any> {
    return this.http.put(this.url, vehicle);
  }

  deleteVehicle(id: number): Observable<any> {
    return this.http.delete(`${this.url}/${id}`);
  }
}
// Path: frontend/src/app/home/home.component.ts
