import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  @Input() products: string[] = ['Bacho', 'Bozhi', 'Buci',
    'Bulgar', 'Djipko', 'Grisho', 'Kobra', 'Kuku', 'Sarai',
    'Maia', 'General', 'Shishi', 'Kasket', 'Cherep', 'KTB', 'Dubai']

  noCoinsAvailable = false;

  constructor() { }

  ngOnInit(): void {
  }

}
