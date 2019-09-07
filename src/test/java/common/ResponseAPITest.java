package common;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.*;

public class ResponseAPITest {

    @Test
    public void testResponseIsCorrect() throws IOException {
        BufferedReader inDave = new BufferedReader(new FileReader("src/test/java/responses/ExampleApiResponseDave.json"));
        String lineDave;
        StringBuilder stringBuilderDave = new StringBuilder();

        while ((lineDave = inDave.readLine()) != null){
            stringBuilderDave.append(lineDave);
        }

        String exampleApiResponseDave = stringBuilderDave.toString();

        ResponseAPI response = new ObjectMapper().readValue(exampleApiResponseDave, ResponseAPI.class);

        List<Option> options = response.getOptions();

        assertTrue(
                response.getSupplier().equals("DAVE") &&
                        response.getPickup().equals("51.470020,-0.454295") &&
                        response.getDropOff().equals("51.00000,112.0000") &&
                        options.size() == 6 &&
                        options.get(0).getCarType().equals("STANDARD") && options.get(0).getPrice().equals("150000") &&
                        options.get(1).getCarType().equals("EXECUTIVE") && options.get(1).getPrice().equals("267000") &&
                        options.get(2).getCarType().equals("LUXURY") && options.get(2).getPrice().equals("200000") &&
                        options.get(3).getCarType().equals("PEOPLE_CARRIER") && options.get(3).getPrice().equals("330000") &&
                        options.get(4).getCarType().equals("LUXURY_PEOPLE_CARRIER") && options.get(4).getPrice().equals("81000") &&
                        options.get(5).getCarType().equals("MINIBUS") && options.get(5).getPrice().equals("230000")

                );
    }
}