import {inject, Injectable} from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Router, CanActivateFn } from '@angular/router';
import { KeycloakService } from "../services/keycloak.service";

@Injectable({
  providedIn: 'root'
})
class PermissionsService {

  constructor(private keycloakService: KeycloakService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    console.log('route.data', route.data)
    const expectedRole = route.data && route.data['expectedRole'];
    const roles = this.keycloakService.getUserRoles();
    const hasRole = roles.includes(expectedRole);

    if (!hasRole) {
      console.log('You are not authorized to access this page');
      this.keycloakService.login()// Redirect to unauthorized page or any other page
    }

    return hasRole;
  }
}

export const AuthGuard: CanActivateFn = (next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean => {
  return inject(PermissionsService).canActivate(next, state);
}
