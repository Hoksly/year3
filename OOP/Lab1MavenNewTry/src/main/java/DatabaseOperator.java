import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;


import Wagons.*;
public class DatabaseOperator {

    private String fileName;


    public DatabaseOperator(String fileName) {
        this.fileName = "jdbc:sqlite:" + fileName;
        recreateDatabase(fileName);

    }

    protected static String correctFileName(String fileName) {
        if (fileName.startsWith("jdbc:sqlite:")) {
            return fileName;
        }
        return "jdbc:sqlite:" + fileName;
    }
    public static void recreateDatabase(String fileName) {
        fileName = correctFileName(fileName);
        try {
            Connection conn = DriverManager.getConnection(fileName);
            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS ParlorWagons " +
                    "(id INTEGER PRIMARY KEY, passengerCapacity INTEGER, " +
                    "luggageCapacity INTEGER, " +
                    "comfortLevel TEXT," +
                    " model TEXT, " +
                    "type TEXT)");

            statement.execute("CREATE TABLE IF NOT EXISTS CompartmentWagons " +
                    "(id INTEGER PRIMARY KEY, passengerCapacity INTEGER, " +
                    "luggageCapacity INTEGER, " +
                    "comfortLevel TEXT," +
                    " model TEXT, " +
                    "type TEXT)");

            statement.execute("CREATE TABLE IF NOT EXISTS LuxWagons " +
                    "(id INTEGER PRIMARY KEY, passengerCapacity INTEGER, " +
                    "luggageCapacity INTEGER, " +
                    "comfortLevel TEXT," +
                    "model TEXT, " +
                    "type TEXT," +
                    "teaType TEXT)");

            statement.execute("CREATE TABLE IF NOT EXISTS PrivateWagons " +
                    "(id INTEGER PRIMARY KEY, passengerCapacity INTEGER, " +
                    "luggageCapacity INTEGER, " +
                    "comfortLevel TEXT," +
                    " model TEXT, " +
                    "type TEXT," +
                    "teaType TEXT," +
                    "owner TEXT)");

            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static PrivateWagon createPrivateWagon(ResultSet resultSet) throws SQLException {


            return new PrivateWagon(resultSet.getInt("passengerCapacity"),
                    resultSet.getInt("luggageCapacity"),
                    Comfort.valueOf(resultSet.getString("comfortLevel")),
                    resultSet.getString("model"),
                    resultSet.getString("teaType"),
                    resultSet.getString("owner"));

    }

    private static LuxWagon createLuxWagon(ResultSet resultSet) throws SQLException {


            return new LuxWagon(resultSet.getInt("passengerCapacity"),
                    resultSet.getInt("luggageCapacity"),
                    Comfort.valueOf(resultSet.getString("comfortLevel")),
                    resultSet.getString("model"),
                    resultSet.getString("teaType"));

    }

    private static CompartmentWagon createCompartmentWagon(ResultSet resultSet) throws SQLException {
        return new CompartmentWagon(resultSet.getInt("passengerCapacity"),
                resultSet.getInt("luggageCapacity"),
                Comfort.valueOf(resultSet.getString("comfortLevel")),
                resultSet.getString("model"));

    }

    private static ParlorWagon createParlorWagon(ResultSet resultSet) throws SQLException {
        return new ParlorWagon(resultSet.getInt("passengerCapacity"),
                resultSet.getInt("luggageCapacity"),
                Comfort.valueOf(resultSet.getString("comfortLevel")),
                resultSet.getString("model"));

    }


    public static ArrayList<PassengerWagon> loadWagons(String fileName) {
        ArrayList<PassengerWagon> wagons = new ArrayList<>();
        Connection conn = null;
        fileName = correctFileName(fileName);
        try {
            conn = DriverManager.getConnection(fileName);

            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM PrivateWagons");

            while (resultSet.next()) {
                wagons.add(createPrivateWagon(resultSet));
            }

            resultSet = statement.executeQuery("SELECT * FROM LuxWagons");
            while (resultSet.next()) {
                wagons.add(createLuxWagon(resultSet));
            }

            resultSet = statement.executeQuery("SELECT * FROM CompartmentWagons");
            while (resultSet.next()) {
                wagons.add(createCompartmentWagon(resultSet));
            }
            resultSet = statement.executeQuery("SELECT * FROM ParlorWagons");
            while (resultSet.next()) {
                wagons.add(createParlorWagon(resultSet));
            }
        }

        catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately, e.g., log or throw a custom exception
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }

        return wagons;
    }

    public static void addPrivateWagon(PrivateWagon wagon, String fileName) {
        fileName = correctFileName(fileName);

        try (Connection conn = DriverManager.getConnection(fileName)) {

            // Check if such wagon does not exist
            String checkQuery = "SELECT * FROM PrivateWagons WHERE model = ? AND type = ? AND passengerCapacity = ? AND luggageCapacity = ? AND comfortLevel = ? AND teaType = ? AND owner = ?";
try (PreparedStatement preparedStatement = conn.prepareStatement(checkQuery)) {
                preparedStatement.setString(1, wagon.getModel());
                preparedStatement.setString(2, "PrivateWagon");
                preparedStatement.setInt(3, wagon.getPassengerCapacity());
                preparedStatement.setInt(4, wagon.getLuggageCapacity());
                preparedStatement.setString(5, wagon.getComfortLevel().toString());
                preparedStatement.setString(6, wagon.getTeaType());
                preparedStatement.setString(7, wagon.getOwner());

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    System.out.println("Such wagon already exists");
                    return;
                }
            }
            String query = "INSERT INTO PrivateWagons " +
                    "(passengerCapacity, luggageCapacity, comfortLevel, model, type, teaType, owner) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, wagon.getPassengerCapacity());
                preparedStatement.setInt(2, wagon.getLuggageCapacity());
                preparedStatement.setString(3, wagon.getComfortLevel().toString());
                preparedStatement.setString(4, wagon.getModel());
                preparedStatement.setString(5, "PrivateWagon");
                preparedStatement.setString(6, wagon.getTeaType());
                preparedStatement.setString(7, wagon.getOwner());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addLuxWagon(LuxWagon wagon, String fileName) {

        fileName = correctFileName(fileName);

        try (Connection conn = DriverManager.getConnection(fileName)) {
            // Check if such wagon does not exist
            String checkQuery = "SELECT * FROM LuxWagons WHERE model = ? AND type = ? AND passengerCapacity = ? AND luggageCapacity = ? AND comfortLevel = ? AND teaType = ?";
try (PreparedStatement preparedStatement = conn.prepareStatement(checkQuery)) {
                preparedStatement.setString(1, wagon.getModel());
                preparedStatement.setString(2, "LuxWagon");
                preparedStatement.setInt(3, wagon.getPassengerCapacity());
                preparedStatement.setInt(4, wagon.getLuggageCapacity());
                preparedStatement.setString(5, wagon.getComfortLevel().toString());
                preparedStatement.setString(6, wagon.getTeaType());

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    System.out.println("Such wagon already exists");
                    return;
                }
            }
            String query = "INSERT INTO LuxWagons " +
                    "(passengerCapacity, luggageCapacity, comfortLevel, model, type, teaType) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, wagon.getPassengerCapacity());
                preparedStatement.setInt(2, wagon.getLuggageCapacity());
                preparedStatement.setString(3, wagon.getComfortLevel().toString()); // or use another representation
                preparedStatement.setString(4, wagon.getModel());
                preparedStatement.setString(5, "LuxWagon");
                preparedStatement.setString(6, wagon.getTeaType());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void addCompartmentWagon(CompartmentWagon wagon, String fileName) {

        fileName = correctFileName(fileName);

        try (Connection conn = DriverManager.getConnection(fileName)) {
            // Check if such wagon does not exist
            String checkQuery = "SELECT * FROM CompartmentWagons WHERE model = ? AND type = ? AND passengerCapacity = ? AND luggageCapacity = ? AND comfortLevel = ?";
try (PreparedStatement preparedStatement = conn.prepareStatement(checkQuery)) {
                preparedStatement.setString(1, wagon.getModel());
                preparedStatement.setString(2, "CompartmentWagon");
                preparedStatement.setInt(3, wagon.getPassengerCapacity());
                preparedStatement.setInt(4, wagon.getLuggageCapacity());
                preparedStatement.setString(5, wagon.getComfortLevel().toString());

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    System.out.println("Such wagon already exists");
                    return;
                }
            }


            String query = "INSERT INTO CompartmentWagons " +
                    "(passengerCapacity, luggageCapacity, comfortLevel, model, type) " +
                    "VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, wagon.getPassengerCapacity());
                preparedStatement.setInt(2, wagon.getLuggageCapacity());
                preparedStatement.setString(3, wagon.getComfortLevel().toString()); // or use another representation
                preparedStatement.setString(4, wagon.getModel());
                preparedStatement.setString(5, "CompartmentWagon");

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void addParlorWagon(ParlorWagon wagon, String fileName) {

        fileName = correctFileName(fileName);

        try (Connection conn = DriverManager.getConnection(fileName)) {

            // Check if such wagon does not exist
            String checkQuery = "SELECT * FROM ParlorWagons WHERE model = ? AND type = ? AND passengerCapacity = ? AND luggageCapacity = ? AND comfortLevel = ?";
try (PreparedStatement preparedStatement = conn.prepareStatement(checkQuery)) {
                preparedStatement.setString(1, wagon.getModel());
                preparedStatement.setString(2, "ParlorWagon");
                preparedStatement.setInt(3, wagon.getPassengerCapacity());
                preparedStatement.setInt(4, wagon.getLuggageCapacity());
                preparedStatement.setString(5, wagon.getComfortLevel().toString());

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    System.out.println("Such wagon already exists");
                    return;
                }
            }

            String query = "INSERT INTO ParlorWagons " +
                    "(passengerCapacity, luggageCapacity, comfortLevel, model, type) " +
                    "VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, wagon.getPassengerCapacity());
                preparedStatement.setInt(2, wagon.getLuggageCapacity());
                preparedStatement.setString(3, wagon.getComfortLevel().toString()); // or use another representation
                preparedStatement.setString(4, wagon.getModel());
                preparedStatement.setString(5, "ParlorWagon");

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<PassengerWagon> getWagonsByComfortLevel(Comfort comfortLevel, String fileName)
    {
        fileName = correctFileName(fileName);
        ArrayList<PassengerWagon> wagons = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(fileName);

            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM PrivateWagons WHERE comfortLevel = '" + comfortLevel.toString() + "'");

            while (resultSet.next()) {
                wagons.add(createPrivateWagon(resultSet));
            }

            resultSet = statement.executeQuery("SELECT * FROM LuxWagons WHERE comfortLevel = '" + comfortLevel.toString() + "'");
            while (resultSet.next()) {
                wagons.add(createLuxWagon(resultSet));
            }

            resultSet = statement.executeQuery("SELECT * FROM CompartmentWagons WHERE comfortLevel = '" + comfortLevel.toString() + "'");
            while (resultSet.next()) {
                wagons.add(createCompartmentWagon(resultSet));
            }
            resultSet = statement.executeQuery("SELECT * FROM ParlorWagons WHERE comfortLevel = '" + comfortLevel.toString() + "'");
            while (resultSet.next()) {
                wagons.add(createParlorWagon(resultSet));
            }
        }

        catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately, e.g., log or throw a custom exception
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }

        return wagons;
    }

    protected static ArrayList<PassengerWagon> getWagonsByCapacity(int capacity, String mode, String fileName)
    {
        fileName = correctFileName(fileName);

        ArrayList<PassengerWagon> wagons = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection( fileName);

            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM PrivateWagons WHERE passengerCapacity" + mode + capacity);

            while (resultSet.next()) {
                wagons.add(createPrivateWagon(resultSet));
            }

            resultSet = statement.executeQuery("SELECT * FROM LuxWagons WHERE passengerCapacity" + mode + capacity);
            while (resultSet.next()) {
                wagons.add(createLuxWagon(resultSet));
            }

            resultSet = statement.executeQuery("SELECT * FROM CompartmentWagons WHERE passengerCapacity" + mode + capacity);
            while (resultSet.next()) {
                wagons.add(createCompartmentWagon(resultSet));
            }
            resultSet = statement.executeQuery("SELECT * FROM ParlorWagons WHERE passengerCapacity" + mode + capacity);
            while (resultSet.next()) {
                wagons.add(createParlorWagon(resultSet));
            }
        }

        catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately, e.g., log or throw a custom exception
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }

        return wagons;
    }

    public static void addWagon(PassengerWagon wagon, String fileName) {
        if (wagon instanceof PrivateWagon) {
            addPrivateWagon((PrivateWagon) wagon, fileName);
        } else if (wagon instanceof LuxWagon) {
            addLuxWagon((LuxWagon) wagon, fileName);
        } else if (wagon instanceof CompartmentWagon) {
            addCompartmentWagon((CompartmentWagon) wagon, fileName);
        } else if (wagon instanceof ParlorWagon) {
            addParlorWagon((ParlorWagon) wagon, fileName);
        }
    }
    public static ArrayList<PassengerWagon> getWagonsByLessCapacity(int capacity, String fileName)
    {
        return getWagonsByCapacity(capacity, " < ", fileName);

    }

    public static ArrayList<PassengerWagon> getWagonsByMoreCapacity(int capacity, String fileName)
    {
        return getWagonsByCapacity(capacity, " > ", fileName);
    }

    public static ArrayList<PassengerWagon> getWagonsByExactCapacity(int capacity, String fileName)
    {
        return getWagonsByCapacity(capacity, " == ", fileName);
    }


    private static ArrayList<PassengerWagon> getWagonsByLuggageCapacity(int capacity, String mode, String fileName)
    {
        ArrayList<PassengerWagon> wagons = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(fileName);

            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM PrivateWagons WHERE luggageCapacity" + mode + capacity);

            while (resultSet.next()) {
                wagons.add(createPrivateWagon(resultSet));
            }

            resultSet = statement.executeQuery("SELECT * FROM LuxWagons WHERE luggageCapacity" + mode + capacity);
            while (resultSet.next()) {
                wagons.add(createLuxWagon(resultSet));
            }

            resultSet = statement.executeQuery("SELECT * FROM CompartmentWagons WHERE luggageCapacity" + mode + capacity);
            while (resultSet.next()) {
                wagons.add(createCompartmentWagon(resultSet));
            }
            resultSet = statement.executeQuery("SELECT * FROM ParlorWagons WHERE luggageCapacity" + mode + capacity);
            while (resultSet.next()) {
                wagons.add(createParlorWagon(resultSet));
            }
        }

        catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately, e.g., log or throw a custom exception
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }

        return wagons;
    }

    public static ArrayList<PassengerWagon> getWagonsByMoreLuggageCapacity(int capacity, String fileName)
    {
       return getWagonsByLuggageCapacity(capacity, " > ", fileName);
    }
    public static ArrayList<PassengerWagon> getWagonsByLessLuggageCapacity(int capacity, String fileName)
    {
        return getWagonsByLuggageCapacity(capacity, " < ", fileName);
    }

    public static ArrayList<PassengerWagon> getWagonsByExactLuggageCapacity(int capacity, String fileName)
    {
        return getWagonsByLuggageCapacity(capacity, " == ", fileName);
    }

}
