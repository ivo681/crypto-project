import {Component, Inject, Input, OnInit} from '@angular/core';
import {LocalStorage} from "../../../core/injection-tokens";
import {MarketService} from "../../market.service";
import {ActivatedRoute, Router} from "@angular/router";
import {BuyService} from "../buy.service";
import {OrderViewModel} from "../../../shared/interfaces/view/order-view-model";
import {PaymentBindingModel} from "../../../shared/interfaces/binding/payment-binding-model";

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css',
    '../../market/market.component.css']
})
export class CheckoutComponent implements OnInit {
  constructor() { }

  ngOnInit(): void {
  }
}
