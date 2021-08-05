import {RouterModule, Routes} from "@angular/router";
import {LoginComponent} from "../user/login/login.component";
import {AuthActivate} from "../../core/guards/auth.activate";
import {RegisterComponent} from "../user/register/register.component";
import {MarketComponent} from "./market/market.component";
import {BuyCoinComponent} from "./buy-coin/buy-coin.component";
import {NgModule} from "@angular/core";
import {from} from "rxjs";
import {CheckoutComponent} from "./checkout/checkout/checkout.component";
import {ConfirmationComponent} from "../shared/components/confirmation/confirmation.component";

const routes: Routes = [
  {
    path: 'market',
    pathMatch: 'full',
    component: MarketComponent,
    canActivate: [AuthActivate],
    data: {
      authenticationRequired: true,
      authenticationFailureRedirectUrl: '/',
    },
    children: []
  },
  {
    path: 'market/buy/:coin',
    component: BuyCoinComponent,
    canActivate: [AuthActivate],
    data: {
      authenticationRequired: true,
      authenticationFailureRedirectUrl: '/',
    }
  },
  {
    path: 'market/checkout/:number',
    component: CheckoutComponent,
    canActivate: [AuthActivate],
    data: {
      authenticationRequired: true,
      authenticationFailureRedirectUrl: '/',
    }
  },
  {
    path: 'market/confirmation/:number',
    component: ConfirmationComponent,
    canActivate: [AuthActivate],
    data: {
      authenticationRequired: true,
      authenticationFailureRedirectUrl: '/',
    }
  }
];

export const MarketRoutingModule = RouterModule.forChild(routes);
