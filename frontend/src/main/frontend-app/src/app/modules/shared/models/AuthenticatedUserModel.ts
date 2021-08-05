export class AuthenticatedUserModel {
  constructor(
    public id: string,
    public username: string,
    public token: string,
    public authorities: {}[]
  ) {}
}
