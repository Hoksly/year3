import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {
  private tokenUrl = 'http://localhost:8080/auth/realms/master/protocol/openid-connect/token'; // replace with your Keycloak server URL

  constructor(private http: HttpClient) { }

  getToken(): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    });

    const body = new URLSearchParams();
    body.set('username', 'admin'); // replace with your admin username
    body.set('password', 'admin'); // replace with your admin password
    body.set('grant_type', 'password');
    body.set('client_id', 'admin-cli');

    return this.http.post(this.tokenUrl, body.toString(), { headers: headers });
  }
}
