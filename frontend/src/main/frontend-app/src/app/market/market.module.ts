import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MarketComponent } from './market/market.component';
import { MarketListComponent } from './market-list/market-list.component';
import { MarketListItemComponent } from './market-list-item/market-list-item.component';



@NgModule({
  declarations: [
    MarketComponent,
    MarketListComponent,
    MarketListItemComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    MarketComponent
  ]
})
export class MarketModule { }
