import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import {CoreModule} from "./core/core.module";
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import {AppRoutingModule} from "./app-routing.module";
import { NotFoundComponent } from './not-found/not-found.component';
import {UserModule} from "./user/user.module";
import {FormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {ProductModule} from "./product/product.module";
import {MarketModule} from "./market/market.module";
import {TokenInterceptor} from "./shared/interceptors/token.interceptor";
import {WalletModule} from "./wallet/wallet.module";
import {TransactionModule} from "./transaction/transaction.module";
import { UnauthorizedComponent } from './unauthorized/unauthorized.component';
import {AdminModule} from "./admin/admin.module";
import {AdminGuard} from "./core/guards/admin.guard";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AboutComponent,
    NotFoundComponent,
    UnauthorizedComponent,
  ],
  imports: [
    BrowserModule,
    AdminModule,
    CoreModule,
    UserModule,
    ProductModule,
    MarketModule,
    TransactionModule,
    WalletModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule
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
