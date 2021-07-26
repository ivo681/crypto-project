import {Routes, RouterModule} from "@angular/router";
import {HomeComponent} from "./home/home.component";
import {AboutComponent} from "./about/about.component";
import {NotFoundComponent} from "./not-found/not-found.component";

const routes : Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: HomeComponent
  },
  {
    path:'about-us',
    pathMatch: 'full',
    component: AboutComponent
  },
  {
    path:'**',
    component: NotFoundComponent
  }
]

export const AppRoutingModule = RouterModule.forRoot(routes);
