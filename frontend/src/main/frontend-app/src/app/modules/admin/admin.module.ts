import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminMenuComponent } from './admin-menu/admin-menu.component';
import {RouterModule} from "@angular/router";
import {DailyTransactionsComponent} from "./daily-transactions/daily-transactions.component";
import {AllTransactionsComponent} from "./all-transactions/all-transactions.component";
import {SharedModule} from "../shared/shared.module";
import {AdminRoutingModule} from "./admin.routing.module";



@NgModule({
  declarations: [
    AdminMenuComponent,
    DailyTransactionsComponent,
    AllTransactionsComponent,
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    RouterModule,
    SharedModule
  ],
  exports: [
    AdminMenuComponent,
    DailyTransactionsComponent,
    AllTransactionsComponent
  ]
})
export class AdminModule { }
