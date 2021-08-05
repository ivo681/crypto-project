import {Inject, Injectable, Renderer2, RendererFactory2} from '@angular/core';
import {IUser} from "../shared/interfaces";
import {LocalStorage} from "../core/injection-tokens";
import {HttpClient} from "@angular/common/http";
import {UserLoginBindingModel} from "../shared/interfaces/binding/user-login-binding-model";
import {UserRegisterBindingModel} from "../shared/interfaces/binding/user-register-binding-model";
import {AuthenticatedUserModel} from "../shared/models/AuthenticatedUserModel";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  user: IUser | undefined;
  private renderer?: Renderer2;

  get isLogged(): boolean {
    let isUserLogged = !!this.user;
    if (isUserLogged){
      this.renderer?.removeClass(document.body, "not-logged");
      this.renderer?.addClass(document.body, "logged")
    } else {
      this.renderer?.addClass(document.body, "not-logged")
    }
    return isUserLogged;
  }

  get userEmail(): string {
    return this.localStorage.getItem('email') || '';
  }

  get isAdmin() : boolean {
    if (this.isLogged){
      // @ts-ignore
      let currentUser = <AuthenticatedUserModel>JSON.parse(localStorage.getItem("<USER>"));
      for (let authority of currentUser.authorities) {
        // @ts-ignore
        if(authority.authority === 'ROLE_ADMIN'){
          return true;
        }
      }
    }
    return false;
  }

  constructor(@Inject(LocalStorage) private localStorage: Window['localStorage'],
              private httpClient: HttpClient, private rendererFactory: RendererFactory2) {
    try {
      const localStorageUser = this.localStorage.getItem('<USER>') || 'ERROR';
      this.user = JSON.parse(localStorageUser);
    } catch {
      this.user = undefined;
    }
    this.renderer = rendererFactory.createRenderer(null, null);
  }



  login(formData: UserLoginBindingModel) {
    return this.httpClient.post<any>("http://localhost:8080/users/login", formData)
  }

  setLoggedInDetails(response: any){
    this.localStorage.setItem(
      'userId',
      response.user.id
    );
    this.localStorage.setItem(
      'email',
      response.user.email
    );
    this.localStorage.setItem(
      'accessToken',
      response.accessToken
    );

    this.user = response.user;
    this.localStorage.setItem('<USER>', JSON.stringify(response.user));
  }

  register(formData: UserRegisterBindingModel) {
    return this.httpClient.post<any>("http://localhost:8080/users/register", formData);
    // return this.httpClient.post(REGISTER_URL, formData, {
    //   reportProgress: true,
    //   observe: 'events'
    // });
  }

  logout(): void {
    this.user = undefined;
    // this.localStorage.removeItem('<USER>');
    this.localStorage.clear();
    this.renderer?.removeClass(document.body, "logged");
    this.renderer?.addClass(document.body, "not-logged")
  }
}
