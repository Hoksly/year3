import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable, switchMap} from 'rxjs';
import { KeycloakService } from './keycloak.service';
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private usersUrl = 'http://localhost:8089/auth/realms/fleet-realm/protocol/openid-connect/registrations'; // replace with your Keycloak server URL and realm

  constructor(private http: HttpClient, private keycloakService: KeycloakService) { }

  createUser(user: any): Observable<any> {
    return this.keycloakService.getToken().pipe(
      switchMap((token: any) => {
        const headers = new HttpHeaders({
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token.access_token}`
        });

        return this.http.post(this.usersUrl, user, { headers: headers });
      })
    );
  }
}
