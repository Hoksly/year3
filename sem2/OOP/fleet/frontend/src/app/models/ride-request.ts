import {User} from "./user";
import {KeycloakService} from "../services/keycloak.service";

export class RideRequest {
  id: number;
  origin: string;
  user: User = new User(0, '', '', '', '');
  destination: string;
  message: string;
  requirements: string;
  fare: number;
  preferredVehicleCategory: string;
  private keycloakService: any;

  constructor(id: number, origin: string, destination: string, message: string, requirements: string, fare: number, preferredVehicleCategory: string) {
    this.id = id;
    this.origin = origin;
    this.destination = destination;
    this.message = message;
    this.requirements = requirements;
    this.fare = fare;
    this.preferredVehicleCategory = preferredVehicleCategory;
  }
}
