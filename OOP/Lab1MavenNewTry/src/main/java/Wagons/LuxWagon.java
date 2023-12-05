package Wagons;

import Wagons.Comfort;
import Wagons.PassengerWagon;

import java.io.IOException;

public class LuxWagon extends PassengerWagon {
    String teaType;
    public LuxWagon(int passengerCapacity,
                    int luggageCapacity,
                    Comfort comfortLevel,
                    String model,
                    String teaType) {
        super(passengerCapacity, luggageCapacity, comfortLevel, model);
        this.teaType = teaType;
    }

    public String getTeaType() {
        return teaType;
    }

    public String toString() {
        return  super.toString() + ", Tea Type: " + teaType;
    }

}
