import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Injectable} from "@angular/core";
import Keycloak from "keycloak-js";
import {User} from "../models/user";

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


    this.keycloak.init({}).then(authenticated => {
      console.log(authenticated ? 'Authenticated' : 'Not authenticated');

      setInterval(() => {
        this.keycloak.updateToken(70).then(refreshed => {
          if (refreshed) {
            console.log('Token refreshed');
          } else {
            console.log('Token not refreshed');
          }
        }).catch(() => {
          console.log('Failed to refresh token');
        });
      }, 60000)
    }).catch(() => {
      console.log("Authenticated Failed");
    });
  }

  // In KeycloakService.ts
  isTokenExpired(): boolean {
    return this.keycloak.isTokenExpired();
  }

  refreshToken(): Promise<boolean> {
    return this.keycloak.updateToken(70);
  }

// In PermissionsService.ts



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

  loadUser(): Promise<User> {
    return this.keycloak.loadUserProfile().then(function(profile) {
      console.log(profile);

      let username = profile.username ? profile.username.toString() : '';
      let email = profile.email ? profile.email : '';
      console.log(username, email);
      return new User(0, username, "", email, "");
    }).catch(function() {
      console.log('failed to load user profile');
      return new User(0, '', '', '', '');
    });
  }
}
