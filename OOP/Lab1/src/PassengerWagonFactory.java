import Wagons.*;

public class PassengerWagonFactory
{
    public static PassengerWagon createWagon(String type, int passengerCapacity, int luggageCapacity, Comfort comfortLevel, String modelName)
    {
        if(type.equals("Lux"))
        {
            return new LuxWagon(passengerCapacity, luggageCapacity, comfortLevel, modelName);
        }
        else if(type.equals("Compartment"))
        {
            return new CompartmentWagon(passengerCapacity, luggageCapacity, comfortLevel, modelName);
        }
        else if (type.equals("Parlor"))
        {
            return new ParlorWagon(passengerCapacity, luggageCapacity, comfortLevel, modelName);
        }
        else if (type.equals("Private"))
        {
            return new PrivateWagon(passengerCapacity, luggageCapacity, comfortLevel, modelName);
        }
        else
        {
            throw new RuntimeException("No such wagon type");
        }
    }

}
