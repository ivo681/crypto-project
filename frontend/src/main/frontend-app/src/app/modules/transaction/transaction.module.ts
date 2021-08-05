import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TransactionsComponent } from './transactions/transactions.component';
import {TransactionsRoutingModule} from "./transaction-routing.module";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {SharedModule} from "../shared/shared.module";



@NgModule({
  declarations: [
    TransactionsComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    TransactionsRoutingModule,
    NgbModule,
  ]
})
export class TransactionModule { }
