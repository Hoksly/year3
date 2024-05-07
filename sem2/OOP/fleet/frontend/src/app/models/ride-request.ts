export class RideRequest {
  public id: number;
  public pickupLocation: string;
  public dropoffLocation: string;
  public timeOption: string;
  public specifiedTime: string;
  public vehicleType: string;

  constructor(id: number, pickupLocation: string, dropoffLocation: string, timeOption: string, specifiedTime: string, vehicleType: string) {
    this.id = id;
    this.pickupLocation = pickupLocation;
    this.dropoffLocation = dropoffLocation;
    this.timeOption = timeOption;
    this.specifiedTime = specifiedTime;
    this.vehicleType = vehicleType;
  }
}
