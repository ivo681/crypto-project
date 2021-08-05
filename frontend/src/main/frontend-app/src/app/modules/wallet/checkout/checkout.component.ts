import {Component, Inject, Input, OnInit} from '@angular/core';
import {OrderViewModel} from "../../shared/interfaces/view/order-view-model";
import {PaymentBindingModel} from "../../shared/interfaces/binding/payment-binding-model";
import {LocalStorage} from "../../../core/injection-tokens";
import {BuyService} from "../../market/checkout/buy.service";
import {ActivatedRoute, Router} from "@angular/router";
import {SellService} from "../sell.service";

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {
  parentData: any;

  constructor() {}

  ngOnInit(): void {
  }


}
