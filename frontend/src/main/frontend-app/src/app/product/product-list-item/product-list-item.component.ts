import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-product-list-item',
  templateUrl: './product-list-item.component.html',
  styleUrls: ['./product-list-item.component.css',
  '../product-list/product-list.component.css']
})
export class ProductListItemComponent implements OnInit {
  @Input() coin: string | undefined;

  constructor() { }

  ngOnInit(): void {
  }

}
