export class CoinViewModel{
  constructor(
  public index: number,
  public name : string,
  public quantity : number,
  public startPriceChange : string,
  public dailyChange : string,
  public hourlyChange : string,
  public currentPrice : number,
  public startClass : string,
  public dailyClass : string,
  public hourlyClass : string,
  ){}
}
