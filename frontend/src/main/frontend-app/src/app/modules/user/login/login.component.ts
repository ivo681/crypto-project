import { Component, OnInit } from '@angular/core';
import {UserLoginBindingModel} from "../../shared/interfaces/binding/user-login-binding-model";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Router} from "@angular/router";
import {LoginResponse} from "../../shared/interfaces/login-response";
import {AuthenticatedUserModel} from "../../shared/models/AuthenticatedUserModel";
import {UserService} from "../user.service";
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  model: UserLoginBindingModel = {
    email: '',
    password: '',
    id: ''
  };
  invalidCredentials: boolean = false;

  errors: any;

  constructor(private http: HttpClient,
              private router: Router,
              private userService: UserService) { }

  ngOnInit(): void {
  }

  sendLoginRequest(): void{
      this.userService.login(this.model).subscribe((response) => {
          this.userService.setLoggedInDetails(response);
          this.router.navigate(["/"])
        }, error => {
          this.model.password = '';
          this.invalidCredentials = true;
      });
  }

}
