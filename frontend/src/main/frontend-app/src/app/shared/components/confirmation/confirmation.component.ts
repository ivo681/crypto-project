import {Component, Inject, Input, OnInit} from '@angular/core';
import {LocalStorage} from "../../../core/injection-tokens";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-confirmation',
  templateUrl: './confirmation.component.html',
  styleUrls: ['./confirmation.component.css']
})
export class ConfirmationComponent implements OnInit {
  orderNumber : string = this.activatedRoute.snapshot.params['number'];

  constructor(@Inject(LocalStorage) private localStorage: Window['localStorage'],
              private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    console.log(this.orderNumber)
  }

}
