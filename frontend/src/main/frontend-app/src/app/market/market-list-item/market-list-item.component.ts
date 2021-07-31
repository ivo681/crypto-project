import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-market-list-item',
  templateUrl: './market-list-item.component.html',
  styleUrls: ['./market-list-item.component.css',
    '../market-list/market-list.component.css',
    '../market/market.component.css']
})
export class MarketListItemComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
