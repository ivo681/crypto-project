import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AnalyticalService {

  constructor() { }

  calculateChangePercentage(oldPrice: number,currentPrice: number) : string{
    let changePercentageString: string = '';
    if (currentPrice > oldPrice){
      changePercentageString += '+'
    } else if (currentPrice < oldPrice){
      changePercentageString += '-'
    }
    changePercentageString += (((Math.abs(currentPrice - oldPrice)) / oldPrice) * 100).toFixed(2) + '%';
    return changePercentageString;
  }

  appendClass(changePercentage: string){
    let tokens = changePercentage.split('');
    if (tokens[0] === '+'){
      return 'text-success'
    } else if (tokens[0] === '-'){
      return 'text-danger'
    }
    return '';
  }
}
