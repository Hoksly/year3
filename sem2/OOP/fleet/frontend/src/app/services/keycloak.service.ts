import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Injectable} from "@angular/core";
import Keycloak from "keycloak-js";

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {
  private tokenUrl = 'http://localhost:8080/auth/realms/master/protocol/openid-connect/token'; // replace with your Keycloak server URL
  private keycloak: Keycloak;

  constructor(private http: HttpClient) {
    this.keycloak = new Keycloak({
      url: 'http://localhost:8079', // replace with your Keycloak server URL
      realm: 'fleet-realm', // replace with your realm
      clientId: 'fleet-web', // replace with your client ID
    });

    this.keycloak.init({})
      .then(authenticated => {
        console.log(authenticated ? 'Authenticated' : 'Not authenticated');
      })
      .catch(error => {
        console.error('Failed to initialize Keycloak', error);
      });
  }

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

  login(): void {
    this.keycloak.login();
  }

  register(): void {
    this.keycloak.register();
  }

  logout(): void {
    this.keycloak.logout();
  }


  getUserRoles() {
    let realmAccess = this.keycloak.realmAccess;
    if (realmAccess) {
      return realmAccess.roles;
    }
    return [];
  }

  isAuthenticated(): boolean | undefined {
    return this.keycloak.authenticated;
  }
}
