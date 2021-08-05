import {RouterModule, Routes} from "@angular/router";
import {AuthActivate} from "../../core/guards/auth.activate";
import {TransactionsComponent} from "./transactions/transactions.component";

const routes: Routes = [
  {
    path: 'transactions',
    pathMatch: 'full',
    component: TransactionsComponent,
    canActivate: [AuthActivate],
    data: {
      authenticationRequired: true,
      authenticationFailureRedirectUrl: '/',
    }
  },
];

export const TransactionsRoutingModule = RouterModule.forChild(routes);
