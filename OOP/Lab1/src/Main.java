import Wagons.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        System.out.println("Hello and welcome!");
        String menuMessageFile = "data/Menu.txt";

        String menuMessage;

        try {
            menuMessage = new String(Files.readAllBytes(Paths.get(menuMessageFile)));
        } catch (IOException e) {
            throw new RuntimeException("No file with menu content");
        }

        ArrayList<PassengerWagon> wagonList = new ArrayList<>();


        while (true)
        {
            System.console().printf(menuMessage);

            String input = System.console().readLine(); // Read user input

            switch (input)
            {
                case "1":
                    System.out.println("Adding a new wagon. Choose type:" +
                            "\n1. Lux" +
                            "\n2. Compartment" +
                            "\n3. Parlor" +
                            "\n4. Private" +
                            "\n5. Back to main menu");
                    String wagonType = System.console().readLine();

                    switch (wagonType) {
                                           }


                    break;
                case "2":
                    System.out.println("Choose search oprtion: ");
                    System.out.println("1. By comfort level");
                    System.out.println("2. By passenger capacity");
                    String searchOption = System.console().readLine();

                    switch (searchOption)
                    {
                        case
                    }

                    break;
                case "3":
                    System.out.println("You chose option 3");
                    break;
                case "4":
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }

        }

    }
}