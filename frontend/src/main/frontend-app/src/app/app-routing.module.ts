import {Routes, RouterModule} from "@angular/router";
import {HomeComponent} from "./home/home.component";
import {AboutComponent} from "./about/about.component";
import {NotFoundComponent} from "./not-found/not-found.component";
import {ProductsComponent} from "./product/products/products.component";
import {LoginComponent} from "./user/login/login.component";
import {AuthActivate} from "./core/guards/auth.activate";
import {MarketComponent} from "./market/market/market.component";
import {UnauthorizedComponent} from "./unauthorized/unauthorized.component";
import {AdminGuard} from "./core/guards/admin.guard";
import {AdminMenuComponent} from "./admin/admin-menu/admin-menu.component";

const routes : Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: HomeComponent
  },
  {
    path:'about-us',
    pathMatch: 'full',
    component: AboutComponent,
    canActivate: [AuthActivate],
    data: {
      authenticationRequired: false,
      authenticationFailureRedirectUrl: '/',
    }
  },
  {
    path:'products',
    pathMatch: 'full',
    component: ProductsComponent,
    canActivate: [AuthActivate],
    data: {
      authenticationRequired: false,
      authenticationFailureRedirectUrl: '/',
    }
  }
  ,{
    path:'unauthorized',
    pathMatch: 'full',
    component: UnauthorizedComponent,
  },
  {
    path: 'admin',
    pathMatch: 'full',
    component: AdminMenuComponent,
    canActivate:[AuthActivate, AdminGuard],
    data: {
      authenticationRequired: true,
      // authenticationFailureRedirectUrl: '/',
    }
  },
  {
    path: '**' || 'not-found',
    component: NotFoundComponent
  }
]

export const AppRoutingModule = RouterModule.forRoot(routes);
