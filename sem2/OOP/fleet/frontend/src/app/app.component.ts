import { Component, OnInit } from '@angular/core';
import { VehicleService } from './services/vehicle.service';
import { Vehicle } from './models/vehicle';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  vehicles: Vehicle[] = [];

  constructor(private vehicleService: VehicleService) { }

  ngOnInit(): void {
    this.vehicleService.getVehicles().subscribe(
      (data) => {
        this.vehicles = data;
      },
      (error) => {
        console.error('Error fetching vehicles:', error);
      }
    );
  }
}
