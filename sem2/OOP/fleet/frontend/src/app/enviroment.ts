export class Environment {
  private static instance: Environment;
  private _production: boolean;
  private _apiUrl: string;
  private _endpoints: { [key: string]: string };

  private constructor() {
    this._production = false;
    this._apiUrl = 'http://localhost:8080';
    this._endpoints = {
      vehicles: 'vehicles',
      vehicle: 'vehicle'
    };
  }

  static getInstance(): Environment {
    if (!Environment.instance) {
      Environment.instance = new Environment();
    }
    return Environment.instance;
  }

  get production(): boolean {
    return this._production;
  }

  set production(value: boolean) {
    this._production = value;
  }

  get apiUrl(): string {
    return this._apiUrl;
  }

  set apiUrl(value: string) {
    this._apiUrl = value;
  }

  get endpoints(): { [key: string]: string } {
    return this._endpoints;
  }

  getEndpoint(endpointName: string): string {
    return this._endpoints[endpointName];
  }

  set endpoints(value: { [key: string]: string }) {
    this._endpoints = value;
  }
}
