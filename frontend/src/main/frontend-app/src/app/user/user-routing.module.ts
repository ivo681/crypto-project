import {RouterModule, Routes} from "@angular/router";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {AuthActivate} from "../core/guards/auth.activate";


const routes: Routes = [
  {
    path: 'users',
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: '/users/login'
      },
      {
        path:'login',
        component: LoginComponent,
        canActivate: [AuthActivate],
        data: {
          authenticationRequired: false,
          authenticationFailureRedirectUrl: '/',
        }
      },
      {
        path:'register',
        component: RegisterComponent,
        canActivate: [AuthActivate],
        data: {
          authenticationRequired: false,
          authenticationFailureRedirectUrl: '/',
        }
      }
    ]
  },
];

export const UserRoutingModule = RouterModule.forChild(routes);
