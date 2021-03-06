import {Component, Input, OnInit} from '@angular/core';
import {CoinServiceModel} from "../../../interfaces/service/coin-service-model";
import {CoinViewModel} from "../../../interfaces/view/coin-view-model";
import {HttpClient} from "@angular/common/http";
import {MarketService} from "../../../../market/market.service";
import {Router} from "@angular/router";
import {WalletService} from "../../../../wallet/wallet.service";
import {Observable} from "rxjs";
import {AnalyticalService} from "../../../services/analytical/analytical.service";
import {DataTransferService} from "../../../services/data-transfer/data-transfer.service";

@Component({
  selector: 'app-coin-list',
  templateUrl: './coin-list.component.html',
  styleUrls: ['./coin-list.component.css']
})
export class CoinListComponent implements OnInit {
  showList?: Promise<boolean>;
  page = 1;
  pageSize = 5;
  @Input() buy?: boolean;
  coins: CoinServiceModel[] = [];
  @Input() coinsForView: CoinViewModel[] = [];

  constructor(private httpClient: HttpClient,
              private marketService: MarketService,
              private walletService: WalletService,
              private dataTransferService: DataTransferService,
              private router: Router) { }

  ngOnInit(): void {
    let observable;
    if (this.buy){
      observable = this.marketService.getAllAvailableCoins();
    } else {
      observable = this.walletService.getOwnedCoins();
    }
    observable.subscribe((response) => {
        this.coins = response;
        if (this.coins){
          for (let i = 0; i < this.coins.length; i++) {
            let coinViewModel : CoinViewModel = this.dataTransferService.transferCoinDataToViewModel(this.coins[i]);
            coinViewModel.index = i + 1;
            this.coinsForView.push(coinViewModel);
          }
        }
        this.showList = Promise.resolve(true);
      },
      error => {})
  }

}
