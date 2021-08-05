import {AdminMenuComponent} from "./admin-menu/admin-menu.component";
import {AuthActivate} from "../../core/guards/auth.activate";
import {AdminGuard} from "../../core/guards/admin.guard";
import {RouterModule, Routes} from "@angular/router";
import {DailyTransactionsComponent} from "./daily-transactions/daily-transactions.component";
import {AllTransactionsComponent} from "./all-transactions/all-transactions.component";

const routes : Routes = [ {
  path: 'admin',
    pathMatch: 'full',
  component: AdminMenuComponent,
  canActivate:[AuthActivate, AdminGuard],
  data: {
  authenticationRequired: true,
  authenticationFailureRedirectUrl: '/unauthorized',
    }
  },
  {
    path: 'admin/daily-transactions',
    pathMatch: 'full',
    component: DailyTransactionsComponent,
    canActivate:[AuthActivate, AdminGuard],
    data: {
      authenticationRequired: true,
      authenticationFailureRedirectUrl: '/unauthorized',
    }
  },
  {
    path: 'admin/all-transactions',
    pathMatch: 'full',
    component: AllTransactionsComponent,
    canActivate:[AuthActivate, AdminGuard],
    data: {
      authenticationRequired: true,
      authenticationFailureRedirectUrl: '/unauthorized',
    }
  },

]

export const AdminRoutingModule = RouterModule.forRoot(routes);
