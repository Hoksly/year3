import Wagons.Comfort;

import java.util.Scanner;

public class BasicWagonInfo {
    int passengerCapacity;
    int luggageCapacity;
    String model;
    Comfort comfort;

    void getBasicInfo() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter passenger capacity: ");
        passengerCapacity = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter luggage capacity: ");

        luggageCapacity = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Choose comfort level: ");
        System.out.println("1. ExtraLow");
        System.out.println("2. Low");
        System.out.println("3. Normal");
        System.out.println("4. High");
        System.out.println("5. ExtraHigh");

        String comfortLevel = scanner.nextLine();


        switch (comfortLevel) {
            case "1":
                System.out.println("You chose ExtraLow");
                this.comfort = Comfort.ExtraLow;
                break;
            case "2":
                System.out.println("You chose Low");
                this.comfort = Comfort.Low;
                break;
            case "3":
                System.out.println("You chose Normal");
                this.comfort = Comfort.Normal;
                break;
            case "4":
                System.out.println("You chose High");
                this.comfort = Comfort.High;
                break;
            case "5":
                System.out.println("You chose ExtraHigh");
                this.comfort = Comfort.ExtraHigh;
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
        System.out.println("Enter model: ");
        this.model = scanner.nextLine();
    }

}
