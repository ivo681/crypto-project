import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CoinServiceModel} from "../shared/interfaces/service/coin-service-model";
import {CoinViewModel} from "../shared/interfaces/view/coin-view-model";
import {map} from "rxjs/operators";
import {OrderBindingModel} from "../shared/interfaces/binding/order-binding-model";
import {AnalyticalService} from "../shared/services/analytical/analytical.service";

@Injectable({
  providedIn: 'root'
})
export class MarketService {

  constructor(private httpClient: HttpClient) { }

  getAllAvailableCoins() {
    return this.httpClient.get<any>("http://localhost:8080/market")
  }

  getAvailableCoinDetails(coinName: string){
    return this.httpClient.get<any>("http://localhost:8080/market/buy/" + coinName);
  }

  placeOrder(order: OrderBindingModel){
    return this.httpClient.post<any>("http://localhost:8080/market/order", order)
  }

}
