import {Component, Input, OnInit} from '@angular/core';
import {OperationServiceModel} from "../../shared/interfaces/service/operation-service-model";
import {OperationViewModel} from "../../shared/interfaces/view/operation-view-model";
import {TransactionService} from "../transaction.service";
import {DataTransferService} from "../../shared/services/data-transfer/data-transfer.service";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-transactions-list',
  templateUrl: './transactions-list.component.html',
  styleUrls: ['./transactions-list.component.css']
})
export class TransactionsListComponent implements OnInit {
  operations: OperationServiceModel[] = [];
  @Input() operationsForView: OperationViewModel[] = [];

  constructor(private transactionService: TransactionService,
              private dataTransferService: DataTransferService,
              private httpClient: HttpClient) { }

  ngOnInit(): void {
    this.transactionService.getUserTransactions().
    subscribe((response) => {
        this.operations = response;
        if (this.operations){
          for (let operation of this.operations) {
            let operationViewModel : OperationViewModel = this.dataTransferService
              .transferOperationDataToViewModel(operation);
            this.operationsForView.push(operationViewModel);
          }
        }
      },
      error => {})
  }

}
