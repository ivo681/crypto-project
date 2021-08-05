import { Component, OnInit } from '@angular/core';
import {UserService} from "../modules/user/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css',
    '../app.component.css']
})
export class HomeComponent implements OnInit {
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

  constructor(private userService: UserService,
              private router: Router) { }

  ngOnInit(): void {
  }

}
