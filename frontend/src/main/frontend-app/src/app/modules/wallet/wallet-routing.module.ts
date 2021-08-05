import {RouterModule, Routes} from "@angular/router";
import {AuthActivate} from "../../core/guards/auth.activate";
import {WalletComponent} from "./wallet/wallet.component";
import {CheckoutComponent} from "./checkout/checkout.component";
import {ConfirmationComponent} from "../shared/components/confirmation/confirmation.component";

const routes: Routes = [
  {
    path:'wallet',
    pathMatch: 'full',
    component: WalletComponent,
    canActivate: [AuthActivate],
    data: {
      authenticationRequired: true,
      authenticationFailureRedirectUrl: '/',
    }
  },
  {
    path:'wallet/sell/:coin',
    pathMatch: 'full',
    component: CheckoutComponent,
    canActivate: [AuthActivate],
    data: {
      authenticationRequired: true,
      authenticationFailureRedirectUrl: '/',
    }
  },
  {
    path: 'wallet/confirmation/:number',
    component: ConfirmationComponent,
    canActivate: [AuthActivate],
    data: {
      authenticationRequired: true,
      authenticationFailureRedirectUrl: '/',
    }
  }
]

export const WalletRoutingModules = RouterModule.forChild(routes);
