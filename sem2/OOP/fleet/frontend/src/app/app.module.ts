import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { DataService } from './data.service';
import {AppComponent} from "./app.component";
import {LoginComponent} from "./components/login/login.component";
import {RouterModule} from "@angular/router";
import {routes} from "./app.routes";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HomeComponent} from "./components/home/home.component";
import {HeaderComponent} from "./components/header/header.component";
import {RideRequestComponent} from "./components/ride-request/ride-request.component"; // Import DataService
import { MatDatepickerModule } from '@angular/material/datepicker';
import {MatNativeDateModule, MatOption} from '@angular/material/core';
import {MatFormField, MatFormFieldModule} from "@angular/material/form-field";
import {MatSelect} from "@angular/material/select";
import {KeycloakService} from "./services/keycloak.service";
import {RideRequestService} from "./services/ride-request.service";
import {UserSignUpComponent} from "./components/user-sign-up/user-sign-up.component";
import {DriverSignUpComponent} from "./components/driver-sign-up/driver-sign-up.component";
import {VehicleSignUpComponent} from "./components/vehicle-sign-up/vehicle-sign-up.component";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    HeaderComponent,
    RideRequestComponent,
    UserSignUpComponent,
    DriverSignUpComponent,
    VehicleSignUpComponent

  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(routes),
    FormsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormField,
    MatOption,
    MatSelect,
    MatFormFieldModule,
    ReactiveFormsModule


  ],
  providers: [
    DataService,
    KeycloakService,
    RideRequestService

  ],
  bootstrap: [AppComponent],  // Bootstrap AppComponent
  exports: [RouterModule]
})

export class AppModule { }

