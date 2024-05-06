import { Component, OnInit } from '@angular/core';
import { VehicleService } from '../../services/vehicle.service';

@Component({
  selector: 'app-vehicle',
  templateUrl: './vehicle.component.html',
  styleUrls: ['./vehicle.component.css'],
  standalone: true
})
export class VehicleComponent implements OnInit {
  vehicles: any[] = [];

  constructor(private vehicleService: VehicleService) { }

  ngOnInit(): void {


  }
}
