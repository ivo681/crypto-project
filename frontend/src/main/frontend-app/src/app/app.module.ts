import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import {CoreModule} from "./core/core.module";
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import {AppRoutingModule} from "./app-routing.module";
import {UserModule} from "./modules/user/user.module";
import {FormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {ProductModule} from "./modules/product/product.module";
import {MarketModule} from "./modules/market/market.module";
import {TokenInterceptor} from "./modules/shared/interceptors/token.interceptor";
import {WalletModule} from "./modules/wallet/wallet.module";
import {TransactionModule} from "./modules/transaction/transaction.module";
import {AdminModule} from "./modules/admin/admin.module";
import {AdminGuard} from "./core/guards/admin.guard";
import {ErrorsModule} from "./modules/errors/errors.module";
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { DailyTransactionsComponent } from './modules/admin/daily-transactions/daily-transactions.component';
import { AllTransactionsComponent } from './modules/admin/all-transactions/all-transactions.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AboutComponent,
  ],
  imports: [
    BrowserModule,
    NgbModule,
    AdminModule,
    ErrorsModule,
    CoreModule,
    UserModule,
    ProductModule,
    MarketModule,
    TransactionModule,
    WalletModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    NoopAnimationsModule
  ],
  providers: [
    AdminGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
