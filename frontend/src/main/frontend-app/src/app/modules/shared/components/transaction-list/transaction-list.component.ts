import {Component, Input, OnInit} from '@angular/core';
import {OperationServiceModel} from "../../interfaces/service/operation-service-model";
import {OperationViewModel} from "../../interfaces/view/operation-view-model";
import {TransactionService} from "../../../transaction/transaction.service";
import {DataTransferService} from "../../services/data-transfer/data-transfer.service";
import {HttpClient} from "@angular/common/http";
import {AdminService} from "../../../admin/admin.service";

@Component({
  selector: 'app-transaction-list',
  templateUrl: './transaction-list.component.html',
  styleUrls: ['./transaction-list.component.css']
})
export class TransactionListComponent implements OnInit {
  @Input() user?: boolean;
  @Input() daily? : boolean;
  page = 1;
  pageSize = 5;
  operations: OperationServiceModel[] = [];
  @Input() operationsForView: OperationViewModel[] = [];

  constructor(private transactionService: TransactionService,
              private adminService: AdminService,
              private dataTransferService: DataTransferService,
              private httpClient: HttpClient) { }

  ngOnInit(): void {
    if (this.user){
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
    } else {
      let promise;
      if (this.daily){
        promise = this.adminService.getDailyUserTransactions();
      } else {
        promise = this.adminService.getAllUserTransactions();
      }
      promise.subscribe((response) => {
          this.operations = response;
          console.log(response);
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

}
