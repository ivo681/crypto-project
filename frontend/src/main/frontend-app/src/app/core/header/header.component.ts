import { Component, OnInit } from '@angular/core';
import {UserService} from "../../modules/user/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css',
    '../../app.component.css']
})
export class HeaderComponent {
  get isLogged(): boolean {
    return this.userService.isLogged;
  }

  get isAdmin(): boolean{
    return this.userService.isAdmin;
  }

  constructor(private userService: UserService,
              private router: Router) { }

  logout(): void {
    this.userService.logout();
    this.router.navigate(['/']);
  }
}
