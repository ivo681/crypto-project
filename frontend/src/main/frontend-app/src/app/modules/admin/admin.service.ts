import {Inject, Injectable} from '@angular/core';
import {LocalStorage} from "../../core/injection-tokens";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(@Inject(LocalStorage) private localStorage: Window['localStorage'],
              private httpClient: HttpClient) { }

  getAllUserTransactions(){
    return this.httpClient.get<any>("http://localhost:8080/users/all-operations")
  }

  getDailyUserTransactions(){
    return this.httpClient.get<any>("http://localhost:8080/users/daily-operations")
  }
}
