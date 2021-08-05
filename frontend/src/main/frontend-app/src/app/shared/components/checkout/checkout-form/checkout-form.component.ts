import {Component, Inject, Input, OnInit} from '@angular/core';
import {PaymentBindingModel} from "../../../interfaces/binding/payment-binding-model";
import {LocalStorage} from "../../../../core/injection-tokens";
import {BuyService} from "../../../../market/checkout/buy.service";
import {ActivatedRoute, Router} from "@angular/router";
import {OrderViewModel} from "../../../interfaces/view/order-view-model";
import {SellService} from "../../../../wallet/sell.service";

@Component({
  selector: 'app-checkout-form',
  templateUrl: './checkout-form.component.html',
  styleUrls: ['./checkout-form.component.css']
})
export class CheckoutFormComponent implements OnInit {
  orderNumber : string = this.activatedRoute.snapshot.params['number'];
  coinUrlEl : string = this.activatedRoute.snapshot.params['coin'];
  @Input() sellOrderNumber?: string;
  @Input() buy? : boolean;
  noCardFoundWithDetails: boolean = false;
  insufficientUserBalance: boolean = false;
  insufficientSystemBalance: boolean = false;

  model: PaymentBindingModel = {
    fullName: '',
    cardNumber: '',
    validTo: '',
    cvv: 0
  }

  constructor(@Inject(LocalStorage) private localStorage: Window['localStorage'],
              private buyService: BuyService,
              private sellService: SellService,
              private activatedRoute: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
  }

  processRequest(): void {
    if (this.buy){
      return this.sendPaymentRequest();
    }
    return this.sendSellRequest();
  }

  sendPaymentRequest() : void {
    this.noCardFoundWithDetails = false;
    this.insufficientUserBalance = false;
    this.buyService.makePurchase(this.model, this.orderNumber)
      .subscribe((response)=>{
        this.router.navigate(["/market/confirmation/" + response])
      }, error => {
        if (error.status == 400){
          this.router.navigate(['/not-found'])
        } else if (error.status == 404){
          this.noCardFoundWithDetails = true;
        } else if (error.status == 417){
          this.insufficientUserBalance = true;
        } else {
        //server error
        }
      })
  }

  sendSellRequest() : void {
    // @ts-ignore
    this.sellService.makeSell(this.model, this.sellOrderNumber)
      .subscribe((response)=>{
        this.router.navigate(["/wallet/confirmation/" + response])
      }, error => {
        if (error.status == 400){
          this.router.navigate(['/not-found'])
        } else if (error.status == 404){
          this.noCardFoundWithDetails = true;
        } else if (error.status == 417){
          this.insufficientSystemBalance = true;
        } else {
          //server error
        }
      })
  }

}
