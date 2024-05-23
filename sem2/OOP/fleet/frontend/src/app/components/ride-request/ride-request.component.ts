import {Component, OnInit} from '@angular/core';
import {NgForm} from "@angular/forms";
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material/core';
import {RideRequestService} from "../../services/ride-request.service";
import {RideRequest} from "../../models/ride-request";
import {Observable} from "rxjs";
import {KeycloakService} from "../../services/keycloak.service";

export const PICKUP_DATE_FORMATS = {
  parse: {
    dateInput: 'EEE, LLL d',
  },
  display: {
    dateInput: 'EEE, LLL d',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY',
  },
};

@Component({
  selector: 'app-ride-request',
  templateUrl: './ride-request.component.html',
  styleUrls: ['./ride-request.component.css'],
  providers: [
    {provide: MAT_DATE_LOCALE, useValue: 'en-US'},
    {provide: MAT_DATE_FORMATS, useValue: PICKUP_DATE_FORMATS},
  ],
})
export class RideRequestComponent implements OnInit{
  minDate: string = new Date().toISOString().split('T')[0];
  maxDate: string = new Date(new Date().setMonth(new Date().getMonth() + 1)).toISOString().split('T')[0];  startAt = new Date();
  dropoffLocation: string = '';
  pickupLocation: any;
  timeOptions: string[] = ['Now', 'Choose a time'];
  vehicleTypes: string[] = ['Economy', 'Comfort', 'Business'];
  selectedVehicleType: string = 'Economy';
  fare: number = 0;
  message: string = "";

  time: any;
  timeOption: string = 'Now'
  specificTime: string = '';

  constructor(private dateAdapter: DateAdapter<Date>, private rideRequestService: RideRequestService, private keycloakService: KeycloakService) {
    this.dateAdapter.setLocale('en-us');
    this.rideRequestService = rideRequestService;
  }

  async onFormSubmit(form: NgForm): Promise<void> {
    const { pickupLocation, dropoffLocation, time, type } = form.value;
    console.log(form.value);

    console.log(`Drive requested. Pickup: ${pickupLocation}, Dropoff: ${dropoffLocation},Option: ${this.timeOption} , When: ${time}`);
    const request = new RideRequest(0, pickupLocation, dropoffLocation, this.message,"" ,this.fare, this.selectedVehicleType);

    try {
      const user = await this.keycloakService.loadUser();
      request.user = user;
      console.log(user);
      this.rideRequestService.createRideRequest(request).subscribe((response) => {  console.log(response); });
    } catch (error) {
      console.error('Failed to load user', error);
    }
  


  }

  ngOnInit(): void {
  }

}
