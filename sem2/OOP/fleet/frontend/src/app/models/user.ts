export class User {
  id: number;
  userId: string;
  username: string;
  password: string;
  email: string;
  phoneNumber: string;

  constructor(id: number, userId: string, username: string, password: string, email: string, phoneNumber: string) {
    this.id = id;
    this.userId = userId;
    this.username = username;
    this.password = password;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }
}
