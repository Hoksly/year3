import Wagons.PassengerWagon;

import java.util.ArrayList;

public class Locomotive {
    private String model;
    private String databaseFile;
    private ArrayList<PassengerWagon> passengerWagons;

    public Locomotive(String model, String databaseFile) {
        this.model = model;
        this.databaseFile = databaseFile;
        this.passengerWagons = DatabaseOperator.loadWagons(databaseFile);
    }

    public void addPassengerWagon(PassengerWagon passengerWagon) {
        // check if passenger wagon is already in the list
        for (PassengerWagon wagon : passengerWagons) {
            if (wagon.getModel().equals(passengerWagon.getModel())) {
                System.out.println("Wagon with this model already exists");
                return;
            }
        }
        this.passengerWagons.add(passengerWagon);
        DatabaseOperator.addWagon(passengerWagon, this.databaseFile);
    }
    public int getTotalPassengerCapacity() {
        int totalPassengerCapacity = 0;
        for (PassengerWagon passengerWagon : passengerWagons) {
            totalPassengerCapacity += passengerWagon.getPassengerCapacity();
        }
        return totalPassengerCapacity;
    }

    public int getTotalLuggageCapacity() {
        int totalLuggageCapacity = 0;
        for (PassengerWagon passengerWagon : passengerWagons) {
            totalLuggageCapacity += passengerWagon.getLuggageCapacity();
        }
        return totalLuggageCapacity;
    }

    public void sortByComfort()
    {
        this.passengerWagons.sort((o1, o2) -> o1.getComfortLevel().compareTo(o2.getComfortLevel()));
    }
    public void displayPassengerWagons()
    {
        for (PassengerWagon passengerWagon : passengerWagons)
        {
            System.out.println(passengerWagon.toString());
        }
    }
    public ArrayList<PassengerWagon> getPassengerWagons() {
        return passengerWagons;
    }

    public ArrayList<PassengerWagon> getPassengerWagonsByPassengerCapacity(int min, int max) {
        ArrayList<PassengerWagon> passengerWagonsByPassengerCapacity = new ArrayList<>();
        for (PassengerWagon passengerWagon : passengerWagons) {
            if (passengerWagon.getPassengerCapacity() >= min && passengerWagon.getPassengerCapacity() <= max) {
                passengerWagonsByPassengerCapacity.add(passengerWagon);
            }
        }
        return passengerWagonsByPassengerCapacity;
    }

}
