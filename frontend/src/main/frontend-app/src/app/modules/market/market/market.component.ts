import {Component, Input, OnInit} from '@angular/core';
import {CoinViewModel} from "../../shared/interfaces/view/coin-view-model";
import {HttpClient} from "@angular/common/http";
import {MarketService} from "../market.service";
import {CoinServiceModel} from "../../shared/interfaces/service/coin-service-model";

@Component({
  selector: 'app-market',
  templateUrl: './market.component.html',
  styleUrls: ['./market.component.css']
})
export class MarketComponent implements OnInit {

  constructor() {
  }

  ngOnInit() : void{
  }
}
