export class RideRequest {
  id: number;
  pickupLocation: string;
  dropoffLocation: string;
  time: string;
  type: string;

  constructor(id: number, pickupLocation: string, dropoffLocation: string, time: string, type: string) {
    this.id = id;
    this.pickupLocation = pickupLocation;
    this.dropoffLocation = dropoffLocation;
    this.time = time;
    this.type = type;
  }
}
