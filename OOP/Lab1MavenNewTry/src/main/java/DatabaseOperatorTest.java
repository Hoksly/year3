import static org.junit.jupiter.api.Assertions.*;

import Wagons.*;
import org.junit.jupiter.api.*;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DatabaseOperatorTest {

    // Path: src/test/resources/test_database.db

    private static final String TEST_DB_FILE = "test_database.db";

    @BeforeAll
    public static void setUp() {
        // Create test database tables
        DatabaseOperator DatabaseOperator = new DatabaseOperator(TEST_DB_FILE);

        // Insert sample data for testing loadWagons
        insertSampleWagons();
    }

    @AfterAll
    public static void tearDown() {
        // Optionally clean up resources after all tests
    }

    @Test
    public void testRecreateDatabase() {
        // Ensure that the tables are created successfully
        assertTrue(new File(TEST_DB_FILE).exists());
    }

    @Test
    public void testLoadWagons() {
        // Assuming you have data in your test database
        ArrayList<PassengerWagon> wagons = DatabaseOperator.loadWagons(TEST_DB_FILE);

        assertNotNull(wagons);
        assertFalse(wagons.isEmpty());

        // Assuming you inserted sample data, you can add more specific assertions
        PassengerWagon firstWagon = wagons.get(0);
        assertEquals(50, firstWagon.getPassengerCapacity());
        assertEquals(20, firstWagon.getLuggageCapacity());
        // Add more assertions based on the expected data
    }
    @Test
    public void testSearchByComfort()
    {

        ArrayList<PassengerWagon> wagons = DatabaseOperator.getWagonsByComfortLevel(Comfort.High, TEST_DB_FILE);
        assertNotNull(wagons);
        assertFalse(wagons.isEmpty());
        PassengerWagon firstWagon = wagons.get(0);

        assertEquals(wagons.size(), 4);

        for(PassengerWagon wagon : wagons)
        {
            assertEquals(Comfort.High, wagon.getComfortLevel());
        }
    }

    @Test
    public void testSearchByPassengerCapacity()
    {
        ArrayList<PassengerWagon> wagons = DatabaseOperator.getWagonsByLessCapacity(50, TEST_DB_FILE);
        assertNotNull(wagons);

        assertEquals(wagons.size(), 0);

        wagons = DatabaseOperator.getWagonsByMoreCapacity(50, TEST_DB_FILE);
        assertNotNull(wagons);
        assertEquals(wagons.size(), 11);

        for(PassengerWagon wagon : wagons)
        {
            assertTrue(wagon.getPassengerCapacity() > 50);
        }
    }

    // Helper method to insert sample data for testing loadWagons
    private static void insertSampleWagons() {



        DatabaseOperator.addPrivateWagon(new PrivateWagon(50, 20, Comfort.High, "Model1", "TeaType1", "Owner1"), TEST_DB_FILE);
        DatabaseOperator.addLuxWagon(new LuxWagon(60, 25, Comfort.Low, "Model2", "TeaType2"), TEST_DB_FILE);
        DatabaseOperator.addLuxWagon(new LuxWagon(70, 30, Comfort.ExtraHigh, "Model3", "TeaType3"), TEST_DB_FILE);
        DatabaseOperator.addLuxWagon(new LuxWagon(80, 35, Comfort.High, "Model4", "TeaType4"), TEST_DB_FILE);
        DatabaseOperator.addLuxWagon(new LuxWagon(90, 40, Comfort.Low, "Model5", "TeaType5"), TEST_DB_FILE);
        DatabaseOperator.addParlorWagon(new ParlorWagon(100, 45, Comfort.ExtraHigh, "Model6"), TEST_DB_FILE);
        DatabaseOperator.addParlorWagon(new ParlorWagon(110, 50, Comfort.High, "Model7"), TEST_DB_FILE);
        DatabaseOperator.addParlorWagon(new ParlorWagon(120, 55, Comfort.Low, "Model8"), TEST_DB_FILE);
        DatabaseOperator.addParlorWagon(new ParlorWagon(130, 60, Comfort.ExtraHigh, "Model9"), TEST_DB_FILE);
        DatabaseOperator.addParlorWagon(new ParlorWagon(140, 65, Comfort.High, "Model10"), TEST_DB_FILE);
        DatabaseOperator.addCompartmentWagon(new CompartmentWagon(150, 70, Comfort.Low, "Model11"), TEST_DB_FILE);
        DatabaseOperator.addCompartmentWagon(new CompartmentWagon(160, 75, Comfort.ExtraHigh, "Model12"), TEST_DB_FILE);



    }
}
