import { Component, OnInit } from '@angular/core';
import {UserLoginBindingModel} from "../../shared/interfaces/user-login-binding-model";
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
  modelError: UserLoginBindingModel = {
    email: '',
    password: '',
    id: ''
  }
  invalidCredentials: boolean = false;

  errors: any;

  constructor(private http: HttpClient,
              private router: Router,
              private userService: UserService) { }

  ngOnInit(): void {
  }

  sendLoginRequest(): void{
    let url = "http://localhost:8080/users/login";

    console.log("urlSearchParams: " + this.model);

    if(this.model.email != null && this.model.password != null) {
      this.userService.login(this.model).subscribe((response) => {
        // console.log(response);
        // console.log(response.accessToken)

        alert("Successfully logged in!");
          this.userService.setLoggedInDetails(response);
          this.router.navigate(["/"])
        }, error => {
          alert("Error")
          this.invalidCredentials = true;
      });
    }

    // this.http.post(url, this.model).subscribe(
    //   res=>{alert('success')},
    //   err=>{
    //     alert('error');
    //     this.modelError = err.error;
    //     let arr = Object.getOwnPropertyNames(this.modelError);
    //     console.log(arr);
    //     console.log(this.modelError);
    //     console.log(this.model)
    //     this.model.password = '';
    //     // console.log(err)
    //     // console.log(err.message)
    //   }
    // );

    // this.http.post<Observable<boolean>>(url, this.model).subscribe(isValid => {
    //   if (isValid) {
    //     sessionStorage.setItem(
    //       'token',
    //       btoa(this.model.email + ':' + this.model.password)
    //     );
    //     sessionStorage.setItem(
    //       'username',
    //       this.model.email
    //     );
    //     alert("success")
    //     this.router.navigate(['']);
    //   } else {
    //     alert("Authentication failed.")
    //   }
    // });
  }

}
