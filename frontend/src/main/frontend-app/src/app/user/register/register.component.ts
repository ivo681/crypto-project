import { Component, OnInit } from '@angular/core';
import {UserRegisterBindingModel} from "../../shared/interfaces/binding/user-register-binding-model";
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
  existingEmail: boolean = false;
  notOldEnough: boolean = false;
  passwordsDoNotMatch: boolean = false;

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
        this.existingEmail = false;
        this.router.navigate(["/users/login"])},
      err=>{
        console.log(err)
        if(err.status == 409){
          this.existingEmail = true;
        } else if (err.status == 403){
          this.notOldEnough = true;
        } else {
          err.error.confirmPassword !== undefined ? this.passwordsDoNotMatch = true : null;
        }
        this.model.password = '';
        this.model.confirmPassword = '';
      }
    );
  }

  checkPasswordsMatch(): void{
    let notNull: boolean = (this.model.password !== null && this.model.password !== '')
      && (this.model.confirmPassword !== null && this.model.confirmPassword !== '');
    let notMatching : boolean = this.model.password !== this.model.confirmPassword;
    if (notNull && notMatching){
      this.passwordsDoNotMatch = true;
    } else {
      this.passwordsDoNotMatch = false;
    }
  }

}
