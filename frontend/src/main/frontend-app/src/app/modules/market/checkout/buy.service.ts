import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {PaymentBindingModel} from "../../shared/interfaces/binding/payment-binding-model";

@Injectable({
  providedIn: 'root'
})
export class BuyService {

  constructor(private httpClient: HttpClient) { }

  getOrderDetails(orderNumber: number){
    return this.httpClient.get<any>("http://localhost:8080/market/order/" + orderNumber);
  }

  makePurchase(payment: PaymentBindingModel, coinUrlEl: string) {
    return this.httpClient.post<any>("http://localhost:8080/market/checkout/" + coinUrlEl
      , payment)
  }
}
