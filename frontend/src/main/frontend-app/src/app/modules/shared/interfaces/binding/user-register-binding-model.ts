import {IUser} from "../user";

export interface UserRegisterBindingModel extends IUser{
  confirmPassword: String,
  dateOfBirth: String
}
