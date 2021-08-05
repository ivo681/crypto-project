import { Component } from '@angular/core';
import {UserService} from "../../user/user.service";

@Component({
  selector: 'app-admin-menu',
  templateUrl: './admin-menu.component.html',
  styleUrls: ['./admin-menu.component.css']
})
export class AdminMenuComponent {

  get isLogged(): boolean {
    return this.userService.isLogged;
  }

  get isAdmin(): boolean{
    return this.userService.isAdmin;
  }

  get userEmail(): string{
    if (this.isLogged){
      return this.userService.userEmail;
    }
    return '';
  }

  constructor(private userService: UserService) {
  }

}
