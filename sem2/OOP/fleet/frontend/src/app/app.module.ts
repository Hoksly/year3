import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { DataService } from './data.service';
import {AppComponent} from "./app.component";
import {LoginComponent} from "./components/login/login.component";
import {RouterModule} from "@angular/router";
import {routes} from "./app.routes";
import {FormsModule} from "@angular/forms";
import {HomeComponent} from "./components/home/home.component";
import {HeaderComponent} from "./components/header/header.component";
import {RideRequestComponent} from "./components/ride-request/ride-request.component"; // Import DataService
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    HeaderComponent,
    RideRequestComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(routes),
    FormsModule,
    MatDatepickerModule,
    MatNativeDateModule

  ],
  providers: [
    DataService

  ],
  bootstrap: [AppComponent],  // Bootstrap AppComponent
  exports: [RouterModule]
})

export class AppModule { }

