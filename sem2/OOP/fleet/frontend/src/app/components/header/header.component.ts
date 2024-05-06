import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {KeycloakService} from "../../services/keycloak.service";

@Component({
  selector: 'app-header', // Corrected here
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  username: string = "";
  password: string = "";

  constructor(private router: Router, public keycloakService: KeycloakService) {

    this.router = router;
    this.keycloakService = keycloakService;
  }


  navigateToLogin(): void {
    this.keycloakService.login( );
  }
  navigateToDrive(): void {
    this.router.navigate(['/drive']);
  }
  navigateToAbout(): void {
    this.router.navigate(['/about']);
  }
  navigateToHelp(): void {
    this.router.navigate(['/help']);
  }
  navigateToRide(): void {
    this.router.navigate(['/ride']);
  }
  navigateToSignUp(): void {
   this.keycloakService.register()
  }

  navigateToLogOut(): void {
    this.keycloakService.logout();
  }

  navigateToAccount(): void {
    this.keycloakService.register()
  }
}
