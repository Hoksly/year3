import Wagons.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static void printWagonList(ArrayList<PassengerWagon> wagonList)
    {
        for (PassengerWagon wagon : wagonList)
        {
            System.out.println(wagon.toString());
        }
    }


    public static void main(String[] args) {

        System.out.println("Hello and welcome!");
        String menuMessageFile = "data/Menu.txt";

        String databaseFile = "data/database.sqlite";

        DatabaseOperator dbOperator = new DatabaseOperator(databaseFile);

        Locomotive locomotive = new Locomotive("BR 01", databaseFile);

        while(true)
        {
            System.out.println("Choose an option:");
            System.out.println("1. Add a wagon");
            System.out.println("2. Display wagons");
            System.out.println("3. Sort by comfort");
            System.out.println("4. Display total passenger capacity");
            System.out.println("5. Display total luggage capacity");
            System.out.println("6. Search by passenger capacity");


            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            switch (input)
            {
                case "1":
                    System.out.println("Choose a wagon type:");
                    System.out.println("1. Lux wagon");
                    System.out.println("2. Private wagon");
                    System.out.println("3. Compartment wagon");
                    System.out.println("4. Parior wagon");

                    String wagonType = scanner.nextLine();
                    BasicWagonInfo basicWagonInfo = new BasicWagonInfo();
                    String teaType, owner;
                    switch (wagonType)
                    {
                        case "1":
                            basicWagonInfo.getBasicInfo();
                            System.out.println("Enter tea type: ");
                            teaType = scanner.nextLine();
                            locomotive.addPassengerWagon(new LuxWagon(basicWagonInfo.passengerCapacity,
                                    basicWagonInfo.luggageCapacity,
                                    basicWagonInfo.comfort,
                                    basicWagonInfo.model,
                                    teaType));
                            break;
                        case "2":
                            basicWagonInfo.getBasicInfo();
                            System.out.println("Enter tea type: ");
                            teaType = scanner.nextLine();
                            System.out.println("Enter owner: ");
                            owner = scanner.nextLine();
                            locomotive.addPassengerWagon(new PrivateWagon(basicWagonInfo.passengerCapacity,
                                    basicWagonInfo.luggageCapacity,
                                    basicWagonInfo.comfort,
                                    basicWagonInfo.model,
                                    teaType,
                                    owner));
                            break;
                        case "3":
                            basicWagonInfo.getBasicInfo();
                            scanner.nextLine();
                            locomotive.addPassengerWagon(new CompartmentWagon(basicWagonInfo.passengerCapacity,
                                    basicWagonInfo.luggageCapacity,
                                    basicWagonInfo.comfort,
                                    basicWagonInfo.model));
                            break;

                        case "4":
                            basicWagonInfo.getBasicInfo();
                            scanner.nextLine();
                            locomotive.addPassengerWagon(new ParlorWagon(basicWagonInfo.passengerCapacity,
                                    basicWagonInfo.luggageCapacity,
                                    basicWagonInfo.comfort,
                                    basicWagonInfo.model));
                            break;
                    }

                case "2":
                    locomotive.displayPassengerWagons();
                    break;

                case "3":
                    locomotive.sortByComfort();
                    System.out.println("Wagons sorted by comfort");
                    break;

                case "4":
                    System.out.println("Total passenger capacity: " + locomotive.getTotalPassengerCapacity());
                    break;

                case "5":
                    System.out.println("Total luggage capacity: " + locomotive.getTotalLuggageCapacity());
                    break;
                case "6":
                    System.out.println("Enter min passenger capacity: ");
                    int min = scanner.nextInt();
                    System.out.println("Enter max passenger capacity: ");
                    int max = scanner.nextInt();
                    ArrayList<PassengerWagon> passengerWagonsByPassengerCapacity = locomotive.getPassengerWagonsByPassengerCapacity(min, max);
                    printWagonList(passengerWagonsByPassengerCapacity);
                    break;

                case "7":
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid input");
                    break;
            }

        }

    }
}