import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class DataService {

  private baseUrl = 'http://localhost:8080'; // Adjust the base URL according to your backend setup

  constructor(private http: HttpClient) { } // Inject HttpClient in the constructor

  getSomeData(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/vehicles`);
  }



}
