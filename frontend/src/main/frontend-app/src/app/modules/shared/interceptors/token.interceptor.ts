import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';

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
  }
}
