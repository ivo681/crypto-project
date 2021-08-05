import {Inject, Injectable} from '@angular/core';
import {LocalStorage} from "../core/injection-tokens";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  constructor(@Inject(LocalStorage) private localStorage: Window['localStorage'],
              private httpClient: HttpClient) { }

  getUserTransactions(){
    return this.httpClient.get<any>("http://localhost:8080/users/operations")
  }
}
