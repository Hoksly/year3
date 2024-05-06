import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import {RideRequestComponent} from "./components/ride-request/ride-request.component";
import {AuthGuard} from "./guards/role.guards";

export const routes: Routes = [
  { path: 'login', component: LoginComponent, title: 'Login page' },
  { path: '', component: HomeComponent },
  { path: 'ride', component: RideRequestComponent, title: 'Ride request page', canActivate: [AuthGuard], data: { expectedRole: 'client_user' }},
  { path: 'drive', component: RideRequestComponent, title: 'Ride request page', canActivate: [AuthGuard], data: { expectedRole: 'client_driver' }},
  { path: 'unauthorized', component: RideRequestComponent, title: 'Ride request page'},

];
