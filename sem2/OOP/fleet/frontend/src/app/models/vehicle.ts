export class Vehicle  {

  constructor(
    public id: number,
    public model: string,
    public make: string,
    public plateNumber: string,
    public vehicleCategory: string,
    public year: number,
    public color: string,
    public mileage: number,
    public lastService: number,
    public hasAirbags: boolean,
    public hasABS: boolean,
    public condition: string,
    public petFriendly: boolean,
    public childSeatRequired: boolean,
    public info: string,
    public wheelchairAccessible: boolean,
  ) {
  }
}
