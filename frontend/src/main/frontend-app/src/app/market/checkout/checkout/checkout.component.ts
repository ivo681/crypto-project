import {Component, Inject, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css',
    '../../market/market.component.css']
})
export class CheckoutComponent implements OnInit {
  constructor() { }

  ngOnInit(): void {
  }
}
