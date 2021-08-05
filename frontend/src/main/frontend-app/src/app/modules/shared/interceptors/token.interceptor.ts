import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {tap} from 'rxjs/operators';
import {AuthenticatedUserModel} from "../models/AuthenticatedUserModel";

@Injectable()
export class TokenInterceptor implements HttpInterceptor{

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let currentUser: boolean = localStorage.getItem('<USER>') !== null;
    let token = localStorage.getItem('accessToken');
    if (currentUser && token) {
      req = req.clone({
        setHeaders: {
          "Authorization": "Bearer " + token,
        }
      });
    }

    return next.handle(req)
    //   .pipe(tap((event: HttpEvent<any>) => {
    //     if (event instanceof HttpResponse && event.body && event.body.accessToken) {
    //       this.saveToken(event.body);
    //     }
    //   }));
  }

  private saveToken(data : any) {
    let username = data.user.firstName || "";
    username += data.user.lastName ? " " + data.user.lastName : "";
    username = username.trim();

    if (!username) {
      username = data.user.email;
    }

    // let currentUser: AuthenticatedUserModel =
    //   new AuthenticatedUserModel(
    //     data.user.id,
    //     username,
    //     data.accessToken,
    //     data.user.authorities.map((a: { authority: any; }) => a.authority));
    // localStorage.setItem("currentUser", JSON.stringify(currentUser));
  }
}
