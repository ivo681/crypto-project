import {Routes, RouterModule} from "@angular/router";
import {HomeComponent} from "./home/home.component";
import {AboutComponent} from "./about/about.component";
import {ProductsComponent} from "./modules/product/products/products.component";
import {LoginComponent} from "./modules/user/login/login.component";
import {AuthActivate} from "./core/guards/auth.activate";
import {MarketComponent} from "./modules/market/market/market.component";
import {AdminGuard} from "./core/guards/admin.guard";
import {AdminMenuComponent} from "./modules/admin/admin-menu/admin-menu.component";
import {UnauthorizedComponent} from "./modules/errors/unauthorized/unauthorized.component";
import {NotFoundComponent} from "./modules/errors/not-found/not-found.component";
import {ServerErrorComponent} from "./modules/errors/server-error/server-error.component";

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
    path:'server-error',
    pathMatch: 'full',
    component: ServerErrorComponent,
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
