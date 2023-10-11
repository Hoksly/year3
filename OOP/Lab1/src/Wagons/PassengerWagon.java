package Wagons;

import Wagons.BasicWagon;

public class PassengerWagon extends BasicWagon {
    private int passengerCapacity;
    private String model;
    private int luggageCapacity;
    private Comfort comfortLevel;

    public PassengerWagon(int passengerCapacity, int luggageCapacity, Comfort comfortLevel, String model) {
        this.passengerCapacity = passengerCapacity;
        this.luggageCapacity = luggageCapacity;
        this.comfortLevel = comfortLevel;
        this.model = model;
    }

    public PassengerWagon() {
    }


    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public int getLuggageCapacity() {
        return luggageCapacity;
    }

    public Comfort getComfortLevel() {
        return comfortLevel;
    }

    @Override
    public String toString() {
        return "Passenger Capacity: " + passengerCapacity + ", Luggage Capacity: " + luggageCapacity + ", Wagons.Comfort Level: " + comfortLevel;
    }
}
