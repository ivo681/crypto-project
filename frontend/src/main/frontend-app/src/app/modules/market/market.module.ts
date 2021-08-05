import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MarketComponent } from './market/market.component';
import {RouterModule} from "@angular/router";
import {MarketRoutingModule} from "./market-routing.module";
import { BuyCoinComponent } from './buy-coin/buy-coin.component';
import {FormsModule} from "@angular/forms";
import { CheckoutComponent } from './checkout/checkout/checkout.component';
import { ConfirmationComponent } from './confirmation/confirmation.component';
import {SharedModule} from "../shared/shared.module";



@NgModule({
  declarations: [
    MarketComponent,
    BuyCoinComponent,
    CheckoutComponent,
    ConfirmationComponent
  ],
    imports: [
        CommonModule,
        SharedModule,
        RouterModule,
        MarketRoutingModule,
        FormsModule
    ],
  exports: [
    MarketComponent
  ]
})
export class MarketModule { }
