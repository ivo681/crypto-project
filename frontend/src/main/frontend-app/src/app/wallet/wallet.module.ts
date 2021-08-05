import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WalletComponent } from './wallet/wallet.component';
import {RouterModule} from "@angular/router";
import {WalletRoutingModules} from "./wallet-routing.module";
import {FormsModule} from "@angular/forms";
import { SellCoinComponent } from './sell-coin/sell-coin.component';
import { CheckoutComponent } from './checkout/checkout.component';
import {SharedModule} from "../shared/shared.module";



@NgModule({
  declarations: [
    WalletComponent,
    SellCoinComponent,
    CheckoutComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule,
    WalletRoutingModules,
    FormsModule
  ],
  exports:[
    WalletComponent
  ]
})
export class WalletModule { }
