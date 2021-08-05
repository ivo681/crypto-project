import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {PaymentBindingModel} from "../shared/interfaces/binding/payment-binding-model";

@Injectable({
  providedIn: 'root'
})
export class SellService {

  constructor(private httpClient: HttpClient) { }

  getOwnedCoinDetails(coinUrlEl: string){
    return this.httpClient.get<any>("http://localhost:8080/wallet/coin-details/" + coinUrlEl);
  }

  makeSell(payment: PaymentBindingModel, sellOrderNumber: string) {
    return this.httpClient.post<any>("http://localhost:8080/wallet/sell/" + sellOrderNumber
      , payment)
  }
}
