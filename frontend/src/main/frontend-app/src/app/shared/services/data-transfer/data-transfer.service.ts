import { Injectable } from '@angular/core';
import {CoinServiceModel} from "../../interfaces/service/coin-service-model";
import {CoinViewModel} from "../../interfaces/view/coin-view-model";
import {AnalyticalService} from "../analytical/analytical.service";
import {OperationServiceModel} from "../../interfaces/service/operation-service-model";
import {OperationViewModel} from "../../interfaces/view/operation-view-model";

@Injectable({
  providedIn: 'root'
})
export class DataTransferService {

  constructor(private analyticalService: AnalyticalService) { }

  transferCoinDataToViewModel(coinServiceModel: CoinServiceModel) : CoinViewModel{
    let coinViewModel : CoinViewModel = {
      name : coinServiceModel.name,
      quantity : coinServiceModel.quantity,
      startPriceChange : this.analyticalService.calculateChangePercentage(coinServiceModel.initialPrice,
        coinServiceModel.currentPrice),
      dailyChange : this.analyticalService.calculateChangePercentage(coinServiceModel.yesterdaysAvgPrice,
        coinServiceModel.currentPrice),
      hourlyChange : this.analyticalService.calculateChangePercentage(coinServiceModel.previousPrice,
        coinServiceModel.currentPrice),
      currentPrice : coinServiceModel.currentPrice,
      startClass : '',
      dailyClass : '',
      hourlyClass : '',
    }
    coinViewModel.startClass = this.analyticalService.appendClass(coinViewModel.startPriceChange);
    coinViewModel.dailyClass = this.analyticalService.appendClass(coinViewModel.dailyChange);
    coinViewModel.hourlyClass = this.analyticalService.appendClass(coinViewModel.hourlyChange);
    return coinViewModel;
  }

  transferOperationDataToViewModel(operationServiceModel: OperationServiceModel) : OperationViewModel{
    let operationViewModel: OperationViewModel = {
      coinName: operationServiceModel.coinName,
      dateTime: operationServiceModel.dateTime,
      orderNumber: operationServiceModel.orderNumber,
      orderType: operationServiceModel.orderType,
      price: operationServiceModel.price,
      status: operationServiceModel.status
    }
    return operationViewModel;
  }
}
