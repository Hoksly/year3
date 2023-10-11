package Wagons;

import java.io.IOException;

public class PrivateWagon extends LuxWagon {

    String owner;
    public PrivateWagon(int passengerCapacity, int luggageCapacity, Comfort comfortLevel, String modelName, String teaType, String owner) {
        super(passengerCapacity, luggageCapacity, comfortLevel, modelName, teaType);

        this.owner = owner;
    }


}
