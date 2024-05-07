import { Injectable } from '@angular/core';
import { Environment } from '../enviroment';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private static readonly BASE_URL = Environment.getInstance().apiUrl;

  static getEndpointUrl(endpoint: string): string {
    return `${this.BASE_URL}/${endpoint}`;
  }

  static createUrl(endpointUrl: string, params: {[key: string]: any} = {}): string {
    if (params) {
      // Construct query parameters
      const queryParams = Object.keys(params)
        .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(params[key])}`)
        .join('&');
      if (queryParams) {
        endpointUrl += `?${queryParams}`;
      }

    }
    return endpointUrl;
  }
}
