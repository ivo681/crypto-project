import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CheckoutFormComponent } from './components/checkout/checkout-form/checkout-form.component';
import {FormsModule} from "@angular/forms";
import { CheckoutCartComponent } from './components/checkout/checkout-cart/checkout-cart.component';
import { CoinListComponent } from './components/coin-list/coin-list/coin-list.component';
import {RouterModule} from "@angular/router";
import { ConfirmationComponent } from './components/confirmation/confirmation.component';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import { TransactionListComponent } from './components/transaction-list/transaction-list.component';



@NgModule({
  declarations: [
    CheckoutFormComponent,
    CheckoutCartComponent,
    CoinListComponent,
    ConfirmationComponent,
    TransactionListComponent
  ],
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        NgbModule,
    ],
  exports: [
    CheckoutFormComponent,
    CheckoutCartComponent,
    CoinListComponent,
    TransactionListComponent
  ]
})
export class SharedModule { }
