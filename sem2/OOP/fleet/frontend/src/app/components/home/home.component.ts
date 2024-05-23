import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  protected router: Router;
  protected keycloakService: any;
  constructor(router: Router, ) { this.router = router;}

  ngOnInit(): void {
  }


  navigateToLogin() {
    console.log('Navigating to login');
    this.router.navigate(['/login'])
    }

}
