import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CoinServiceModel} from "../shared/interfaces/service/coin-service-model";
import {CoinViewModel} from "../shared/interfaces/view/coin-view-model";
import {AnalyticalService} from "../shared/services/analytical/analytical.service";

@Injectable({
  providedIn: 'root'
})
export class WalletService {

  constructor(private httpClient: HttpClient,
              private analyticalService: AnalyticalService) { }

  getOwnedCoins() {
    return this.httpClient.get<any>("http://localhost:8080/wallet")
  }
}
