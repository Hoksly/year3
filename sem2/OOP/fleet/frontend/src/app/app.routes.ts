import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import {RideRequestComponent} from "./components/ride-request/ride-request.component";

export const routes: Routes = [
  { path: 'login', component: LoginComponent, title: 'Login page' },
  { path: '', component: HomeComponent },
  { path: 'ride-request', component: RideRequestComponent, title: 'Ride request page'}
];
