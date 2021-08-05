import {Component, EventEmitter, Inject, Input, OnInit, Output} from '@angular/core';
import {OrderViewModel} from "../../../interfaces/view/order-view-model";
import {LocalStorage} from "../../../../../core/injection-tokens";
import {BuyService} from "../../../../market/checkout/buy.service";
import {ActivatedRoute, Router} from "@angular/router";
import {SellService} from "../../../../wallet/sell.service";

@Component({
  selector: 'app-checkout-cart',
  templateUrl: './checkout-cart.component.html',
  styleUrls: ['./checkout-cart.component.css']
})
export class CheckoutCartComponent implements OnInit {
  buyOrderNumber : string = this.activatedRoute.snapshot.params['number'];
  coinName : string = this.activatedRoute.snapshot.params['coin'];
  @Output() redirect: EventEmitter<any> = new EventEmitter();
  sellOrderNumber : string = ''

  @Input() buy? : boolean;
  order: OrderViewModel={
    coinName: '',
    coinQuantity: 0,
    total: '0',
    coinFee: '0',
    commissionFee: '0',
    orderNumber: 0
  }

  constructor(@Inject(LocalStorage) private localStorage: Window['localStorage'],
              private buyService: BuyService,
              private sellService: SellService,
              private activatedRoute: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    if (this.buy){
      this.buyService.getOrderDetails(Number(this.buyOrderNumber))
        .subscribe((response)=>{
          this.order.coinName = response.coinName;
          this.order.coinQuantity = response.coinQuantity;
          this.order.orderNumber = response.orderNumber;
          this.order.total = response.total;
          this.order.coinFee = (response.total / 1.05).toFixed(2);
          this.order.commissionFee = (response.total - (response.total / 1.05)).toFixed(2);
        }, error => {
          if (error.status == 404){
            this.router.navigate(['/not-found']);
          } else {
            this.router.navigate(['/server-error'])
          }
        })
    } else {
      this.sellService.getOwnedCoinDetails(this.coinName)
        .subscribe((response)=>{
          this.sellOrderNumber = response.orderNumber;
          this.redirect.emit(this.sellOrderNumber);
          this.order.orderNumber = response.orderNumber;
          this.order.coinName = response.coinModel.name;
          this.order.coinQuantity = response.coinModel.quantity;
          this.order.coinFee = (this.order.coinQuantity * response.coinModel.currentPrice).toFixed(2);
          this.order.commissionFee = (((this.order.coinQuantity * response.coinModel.currentPrice)* 1.05) -
            (this.order.coinQuantity * response.coinModel.currentPrice))
            .toFixed(2);
          this.order.total = (Number(this.order.coinFee) - Number(this.order.commissionFee)).toFixed(2);
        }, error => {
          if (error.status == 404){
            this.router.navigate(['/not-found']);
          } else {
            this.router.navigate(['/server-error'])
          }
        })
    }
  }

}
