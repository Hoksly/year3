import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { JSEncrypt } from 'jsencrypt';
import {firstValueFrom, Observable} from "rxjs";
import {ApiService} from "../utils/url.composer";
import {Environment} from "../enviroment";
import { enc, lib, AES } from "crypto-js";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private publicKey: string = "";
  private jsEncrypt = new JSEncrypt();
  private endpoint: string;

  constructor(private http: HttpClient) {
    this.endpoint = ApiService.getEndpointUrl(Environment.getInstance().getEndpoint('auth'));
  }

  protected getPublicKey(): Promise<string> {
    if (this.publicKey) {
      return Promise.resolve(this.publicKey);
    } else {
      let url = ApiService.createUrl(ApiService.createUrl(ApiService.getEndpointUrl(Environment.getInstance().getEndpoint('publicKey')), {  }));
      console.log(url);
      const publicKey$ = this.http.get(url, {responseType: 'text'});

      return firstValueFrom(publicKey$)
        .then((publicKey: any) => {
          this.publicKey = publicKey.toString();
          return this.publicKey;
        });
    }
  }

  public async encryptMessage(message: string): Promise<string> {
    const symmetricKey = this.generateSymmetricKey();
    const encryptedData = AES.encrypt(message, "fzES4MGmY1c19vW6").toString();

    console.log("Encrypted data AES: ", encryptedData)
    try {
      const publicKey = await this.getPublicKey();
      this.jsEncrypt.setPublicKey(publicKey);
      const encryptedKey = this.jsEncrypt.encrypt(symmetricKey);

      return JSON.stringify({
        data: encryptedData,
        key: encryptedKey
      });
    } catch (error) {
      console.error('Error encrypting message:', error);
      throw error;
    }
  }

  private generateSymmetricKey(): string {
    // Generate a random 256-bit (32-byte) key
    const keySizeBytes = 16;
    const randomBytes = new Uint8Array(keySizeBytes);
    window.crypto.getRandomValues(randomBytes);

    // Convert the random bytes to a hexadecimal string
    let key = '';
    randomBytes.forEach((byte) => {
      key += ('0' + byte.toString(16)).slice(-2);
    });

    console.log("Symmetric key: ", key)
    return "fzES4MGmY1c19vW6";
  }



  async signUpUser(userData: any): Promise<Observable<any>> {
    const endpoint = ApiService.getEndpointUrl(Environment.getInstance().getEndpoint('userSignUp'));
    console.log((JSON.stringify(userData)));
    const encryptedData = await this.encryptMessage(JSON.stringify(userData));
    console.log("Encrypted message: ", encryptedData)
    return this.http.post(endpoint, encryptedData);

  }

  async signUpDriver(driverData: any): Promise<Observable<any>> {
    const endpoint = ApiService.getEndpointUrl('driverSignUp');
    const encryptedData = await this.encryptMessage(JSON.stringify(driverData));
    return this.http.post(endpoint, encryptedData);
  }

}
