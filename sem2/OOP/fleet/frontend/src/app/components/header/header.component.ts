import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header', // Corrected here
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  constructor(private router: Router) { }

  navigateToLogin(): void {
    this.router.navigate(['/login']);
  }
  navigateToDrive(): void {
    this.router.navigate(['/drive']);
  }
  navigateToAbout(): void {
    this.router.navigate(['/about']);
  }
  navigateToHelp(): void {
    this.router.navigate(['/help']);
  }
  navigateToRide(): void {
    this.router.navigate(['/ride']);
  }
}
