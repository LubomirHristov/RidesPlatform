package common;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class SupplierTest {

    @Test
    public void doesSupplierHandleExceptionsGracefully(){
        Supplier supplier = new Supplier("john");
        String pickup = "51.470020,-0.454295";
        String dropOff = "51.00000,112.0000";
        String passengers = "4";

        Map<String, String> result = supplier.getOptions(pickup, dropOff, passengers);

        assertTrue(result.isEmpty());
    }
}