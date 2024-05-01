import { Component } from '@angular/core';
import Keycloak from 'keycloak-js';
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  keycloak: Keycloak;
  username: string = "";
  password: string = "";
  router: Router;

  constructor(router: Router) {
    this.keycloak = new Keycloak({
      url: 'http://localhost:8079', // replace with your Keycloak server URL
      realm: 'fleet-realm', // replace with your realm
      clientId: 'fleet-web', // replace with your client ID
    });

    this.keycloak.init({ onLoad: 'check-sso' }).then(authenticated => {
      if (authenticated) {

      } else {
        // User is not authenticated
      }
    }).catch(() => {
      console.error("Failed to initialize Keycloak");
    });

    this.router = router;
  }

  onLogin() {
    this.keycloak.login( );
  }
}
