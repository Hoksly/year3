import java.sql.*;
import java.util.ArrayList;

import Wagons.*;
public class DatabaseOperator {
    public static void recreateDatabase(String fileName) {
        // recreate database
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + fileName);
            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS wagons (id INTEGER PRIMARY KEY, passengerCapacity INTEGER, luggageCapacity INTEGER, comfortLevel TEXT, model TEXT, type TEXT)");
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<PassengerWagon> loadWagons(String fileName)
    {
        ArrayList<PassengerWagon> wagons = new  ArrayList<PassengerWagon>();

        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + fileName);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM wagons");
            while (rs.next()) {
                String type = rs.getString("type");
                if (type.equals("Private")) {
                    wagons.add(new PrivateWagon(rs.getInt("passengerCapacity"), rs.getInt("luggageCapacity"), Comfort.valueOf(rs.getString("comfortLevel")), rs.getString("model")));
                } else if (type.equals("Lux")) {
                    wagons.add(new LuxWagon(rs.getInt("passengerCapacity"), rs.getInt("luggageCapacity"), Comfort.valueOf(rs.getString("comfortLevel")), rs.getString("model")));
                }
                else if (type.equals("Compartment")) {
                    wagons.add(new CompartmentWagon(rs.getInt("passengerCapacity"), rs.getInt("luggageCapacity"), Comfort.valueOf(rs.getString("comfortLevel")), rs.getString("model")));
                } else if (type.equals("Parlor")) {
                    wagons.add(new ParlorWagon(rs.getInt("passengerCapacity"), rs.getInt("luggageCapacity"), Comfort.valueOf(rs.getString("comfortLevel")), rs.getString("model")));
                }
            }
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return wagons;
    }



}
