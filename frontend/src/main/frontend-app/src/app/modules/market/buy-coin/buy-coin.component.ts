import {Component, Inject, Input, OnInit} from '@angular/core';
import {MarketService} from "../market.service";
import {ActivatedRoute, Router} from "@angular/router";
import {CoinBuyViewModel} from "../../shared/interfaces/view/coin-buy-view-model";
import {OrderBindingModel} from "../../shared/interfaces/binding/order-binding-model";
import {LocalStorage} from "../../../core/injection-tokens";
import {AnalyticalService} from "../../shared/services/analytical/analytical.service";

@Component({
  selector: 'app-buy-coin',
  templateUrl: './buy-coin.component.html',
  styleUrls: ['./buy-coin.component.css']
})
export class BuyCoinComponent implements OnInit {
  coinUrlEl : string = this.activatedRoute.snapshot.params['coin'];
  showCoin?: Promise<boolean>;
  unsuccessfulOrder: boolean = false;
  @Input() total: string = '0';
  // @ts-ignore
  @Input() coin: CoinBuyViewModel;
  coinName: string = this.coinUrlEl[0].toUpperCase() + this.coinUrlEl.slice(1);
  model : OrderBindingModel = {
    name: '',
    quantity : 0,
    price : 0,
    userEmail : this.localStorage.getItem('email') || ''
  };

  constructor(@Inject(LocalStorage) private localStorage: Window['localStorage'],
              private marketService: MarketService,
              private analyticalService: AnalyticalService,
              private activatedRoute: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    this.model.name = this.coinName;
    this.marketService.getAvailableCoinDetails(this.coinName).subscribe((response) => {
        this.coin = response;
        this.coin.startPriceChange = this.analyticalService.calculateChangePercentage(
          this.coin.initialPrice, this.coin.currentPrice);
        this.coin.dailyChange = this.analyticalService.calculateChangePercentage(
          this.coin.yesterdaysAvgPrice, this.coin.currentPrice);
        this.coin.hourlyChange = this.analyticalService.calculateChangePercentage(
            this.coin.previousPrice, this.coin.currentPrice);
        this.coin.startClass = this.analyticalService.appendClass(this.coin.startPriceChange);
        this.coin.dailyClass = this.analyticalService.appendClass(this.coin.dailyChange);
        this.coin.hourlyClass = this.analyticalService.appendClass(this.coin.hourlyChange);
        this.model.price = this.coin.currentPrice;
        this.showCoin = Promise.resolve(true);
      },
      error => {
        this.router.navigate(["/not-found"])
      });
  }

  changeTotalAmount(quantity: number): void{
    this.total = (quantity * this.coin.currentPrice).toFixed(2);
  }

  sendOrderRequest() : void {
    this.marketService.placeOrder(this.model).subscribe((response) => {
      let orderNumber : number = response;
      this.router.navigate(["/market/checkout/" + orderNumber])
    }, error => {
      this.unsuccessfulOrder = true;
    });
  }
}
