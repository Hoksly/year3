package DataManager;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ConsoleManagement.InputManager;

import Entity.Concert;

public class ConcertFactory {

    public final static List<String> faculties = new ArrayList<>(Arrays.asList("FCSC", "FIT"));

    public static Concert makeConcert(InputManager inputManager, String ID, List<String> sources) {
        System.out.println("\nYou are in 'Make your Concert!' menu, now make your concert:\n");
        Concert concert = new Concert(ID);
        concert.setName(inputManager.getString("Enter name: "));
        concert.setLocation(inputManager.getString("Enter location: "));
        concert.setDate(inputManager.getDate("Enter date: ").toString());
        concert.setTime(inputManager.getString("Enter time: "));
        concert.setPrice(inputManager.getInt("Enter price: ", 0, Integer.MAX_VALUE));
        concert.setGenre(inputManager.getOption(faculties, "Enter genre: "));
        concert.setSeats(inputManager.getInt("Enter number of seats: ", 0, Integer.MAX_VALUE));


        while(sources != null &&  inputManager.getBool("Add artist? (+/-): ")){
            concert.addArtist(inputManager.getOption(sources, "Enter artist: "));
        }
        return concert;
    }

    public static Concert updateConcert(InputManager inputManager, Concert student) {
        if (student == null) {
            return null;
        }
        System.out.println("\nYou are in 'Update your Concerts!' menu, now modify your concert:\n");
        String input;

        return student;
    }


    public static void sort(List<Concert> list) {
        Collections.sort(list, new Comparator<Concert>() {

            @Override
            public int compare(Concert o1, Concert o2) {
                return o1.getName().compareTo(o2.getName());
            }

        });
    }

}