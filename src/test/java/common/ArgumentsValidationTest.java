package common;

import console.ConsoleSearchEngine;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ArgumentsValidationTest {

    @Test
    public void testValidNumberOfCoordinatesPassed(){
        String pickup = "51,17,13";
        String dropOff = "151,27,14";
        String passengers = "4";

        String result = ConsoleSearchEngine.validateArguments(pickup, dropOff, passengers);

        assertEquals("Invalid coordinates.", result);
    }

    @Test
    public void testCoordinatesAreValidNumbers(){
        String pickup = "a,b";
        String dropOff = "c,d";
        String passengers = "4";

        String result = ConsoleSearchEngine.validateArguments(pickup, dropOff, passengers);

        assertEquals("Invalid coordinates.", result);
    }

    @Test
    public void testNumberOfPassengersIsNotNegative(){
        String pickup = "51.470020,-0.454295";
        String dropOff = "51.00000,112.0000";
        String passengers = "-4";

        String result = ConsoleSearchEngine.validateArguments(pickup, dropOff, passengers);

        assertEquals("Invalid number of passengers.", result);
    }

    @Test
    public void testNumberOfPassengersIsANumber(){
        String pickup = "51.470020,-0.454295";
        String dropOff = "51.00000,112.0000";
        String passengers = "abc";

        String result = ConsoleSearchEngine.validateArguments(pickup, dropOff, passengers);

        assertEquals("Invalid number of passengers.", result);
    }

    @Test
    public void testValidArgumentsPass(){
        String pickup = "51.470020,-0.454295";
        String dropOff = "51.00000,112.0000";
        String passengers = "4";

        String result = ConsoleSearchEngine.validateArguments(pickup, dropOff, passengers);

        assertEquals("", result);
    }

    @Test
    public void testSupplierExists(){
        List<String> suppliers = Arrays.asList("dave", "eric", "jeff");
        String supplier = "dave";

        String result = ConsoleSearchEngine.validateSupplier(suppliers, supplier);

        assertEquals("", result);
    }

    @Test
    public void testSupplierDoesNotExists(){
        List<String> suppliers = Arrays.asList("dave, eric, jeff");
        String supplier = "john";

        String result = ConsoleSearchEngine.validateSupplier(suppliers, supplier);

        assertEquals(String.format("Supplier %s does not exist.", supplier), result);
    }
}
