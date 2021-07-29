import { Component, OnInit } from '@angular/core';
import {UserRegisterBindingModel} from "../../shared/interfaces/user-register-binding-model";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {UserService} from "../user.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  model: UserRegisterBindingModel = {
    email: '',
    password: '',
    confirmPassword: '',
    dateOfBirth: '',
    id: ''
  }

  modelError: UserRegisterBindingModel = {
    email: '',
    password: '',
    confirmPassword: '',
    dateOfBirth: '',
    id: ''
  }
  existingEmail: boolean = false;

  errors: any;

  constructor(private http: HttpClient,
              private router: Router,
              private userService: UserService) { }

  ngOnInit(): void {
  }

  sendRegisterRequest(): void{
    let url = "http://localhost:8080/users/register";
    console.log(this.model)

    this.userService.register(this.model).subscribe(
      res=>{alert('success');
        this.existingEmail = false;},
      err=>{
        alert('error');
        this.modelError = err.error;
        if(err.status == 409){
          this.existingEmail = true;
        }
        this.model.password = '';
        this.model.confirmPassword = '';
        // console.log(err)
        // console.log(err.message)
      }
    );
  }

}
