import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import {UserRoutingModule} from "./user-routing.module";
import {FormsModule} from "@angular/forms";
import { PopoverComponent } from './login/popover/popover.component';



@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    PopoverComponent
  ],
    imports: [
        CommonModule,
        UserRoutingModule,
        FormsModule
    ]
})
export class UserModule { }
