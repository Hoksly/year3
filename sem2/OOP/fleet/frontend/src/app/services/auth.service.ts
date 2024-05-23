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
  private aesKey: lib.WordArray = lib.WordArray.random(16);
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

    const aesKey = await this.getAESKey();
    const wordArray = enc.Base64.parse(aesKey.toString());
    console.log("AES Key: ", wordArray)
    const encryptedData = AES.encrypt(message, aesKey.toString()).toString();

    console.log("Encrypted data AES: ", encryptedData)
    try {
     // const publicKey = await this.getPublicKey();
      // this.jsEncrypt.setPublicKey(publicKey);
      // const encryptedKey = this.jsEncrypt.encrypt(this.aesKey);

      return JSON.stringify(encryptedData);
    } catch (error) {
      console.error('Error encrypting message:', error);
      throw error;
    }
  }

  protected getAESKey(): Promise<lib.WordArray> {
      if (false) {
      return Promise.resolve(this.aesKey);
    } else {
      let url = ApiService.createUrl(ApiService.createUrl(ApiService.getEndpointUrl(Environment.getInstance().getEndpoint('aesKey')), {  }));
      console.log(url);
      const aesKey$ = this.http.get(url, {responseType: 'text'});
      console.log("AES Key: ", aesKey$)
      return Promise.resolve(lib.WordArray.random(16));
    }

  }



  async signUpUser(userData: any): Promise<Observable<any>> {
    const endpoint = ApiService.getEndpointUrl(Environment.getInstance().getEndpoint('userSignUp'));
    console.log((JSON.stringify(userData)));

    return this.http.post(endpoint, JSON.stringify(userData));

  }

  async signUpDriver(driverData: any): Promise<Observable<any>> {
    const endpoint = ApiService.getEndpointUrl('driverSignUp');

    const encryptedData = await this.encryptMessage(JSON.stringify(driverData));
    return this.http.post(endpoint, encryptedData);
  }

}
