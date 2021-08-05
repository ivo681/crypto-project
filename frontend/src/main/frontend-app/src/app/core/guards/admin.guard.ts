import { Injectable } from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {UserService} from "../../user/user.service";

@Injectable()
export class AdminGuard implements CanActivate {
  constructor(
    private userService : UserService,
    private router : Router
  ) {

  }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) : boolean {
    debugger
    if (this.userService.isAdmin) {
      return true;
    }

    this.router.navigate(["/unauthorized"]);
    return false;
  }
}
