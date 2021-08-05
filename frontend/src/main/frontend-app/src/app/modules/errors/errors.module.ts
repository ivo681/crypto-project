import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotFoundComponent } from './not-found/not-found.component';
import { UnauthorizedComponent } from './unauthorized/unauthorized.component';
import { ServerErrorComponent } from './server-error/server-error.component';
import {RouterModule} from "@angular/router";



@NgModule({
  declarations: [
    NotFoundComponent,
    UnauthorizedComponent,
    ServerErrorComponent
  ],
  imports: [
    CommonModule,
    RouterModule
  ],
  exports:[
    NotFoundComponent,
    UnauthorizedComponent,
    ServerErrorComponent
  ]
})
export class ErrorsModule { }
