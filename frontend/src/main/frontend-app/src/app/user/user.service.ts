import {Inject, Injectable} from '@angular/core';
import {IUser} from "../shared/interfaces";
import {LocalStorage} from "../core/injection-tokens";
import {HttpClient} from "@angular/common/http";
import {UserLoginBindingModel} from "../shared/interfaces/user-login-binding-model";
import {UserRegisterBindingModel} from "../shared/interfaces/user-register-binding-model";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  user: IUser | undefined;

  get isLogged(): boolean {
    return !!this.user;
  }

  constructor(@Inject(LocalStorage) private localStorage: Window['localStorage'],
              private httpClient: HttpClient) {
    try {
      const localStorageUser = this.localStorage.getItem('<USER>') || 'ERROR';
      this.user = JSON.parse(localStorageUser);
    } catch {
      this.user = undefined;
    }
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
  }
}
