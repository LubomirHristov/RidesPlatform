package common;

import console.ConsoleSearchEngine;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

import com.fasterxml.jackson.databind.ObjectMapper;


public class SearchEngineTest {
    private String exampleApiResponseDave;
    private String exampleApiResponseEric;
    private String exampleApiResponseJeff;

    private SearchEngine searchEngine = new SearchEngine();

    public SearchEngineTest() throws IOException {
        BufferedReader inDave = new BufferedReader(new FileReader("src/test/java/responses/ExampleApiResponseDave.json"));
        String lineDave;
        StringBuilder stringBuilderDave = new StringBuilder();

        while ((lineDave = inDave.readLine()) != null){
            stringBuilderDave.append(lineDave);
        }

        exampleApiResponseDave = stringBuilderDave.toString();

        BufferedReader inEric = new BufferedReader(new FileReader("src/test/java/responses/ExampleApiResponseEric.json"));
        String lineEric;
        StringBuilder stringBuildeEricr = new StringBuilder();

        while ((lineEric = inEric.readLine()) != null){
            stringBuildeEricr.append(lineEric);
        }

        exampleApiResponseEric = stringBuildeEricr.toString();

        BufferedReader inJeff = new BufferedReader(new FileReader("src/test/java/responses/ExampleApiResponseJeff.json"));
        String lineJeff;
        StringBuilder stringBuilderJeff = new StringBuilder();

        while ((lineJeff = inJeff.readLine()) != null){
            stringBuilderJeff.append(lineJeff);
        }

        exampleApiResponseJeff = stringBuilderJeff.toString();
    }

    @Test
    public void testOnlyRelevantOptionsShown() throws IOException {
        ResponseAPI responseDave = new ObjectMapper().readValue(exampleApiResponseDave, ResponseAPI.class);

        String passengers = "6";

        List<Option> allOptions = responseDave.getOptions();

        Map<String, String> filteredOptions = new Supplier("dave").filterOptions(passengers, allOptions);

        assertTrue(filteredOptions.size() == 3 && filteredOptions.containsKey("PEOPLE_CARRIER") &&
                filteredOptions.containsKey("LUXURY_PEOPLE_CARRIER") && filteredOptions.containsKey("MINIBUS"));
    }

    @Test
    public void testOnlyTheBestPriceForACarTypeShown() throws IOException {
        ResponseAPI responseDave = new ObjectMapper().readValue(exampleApiResponseDave, ResponseAPI.class);
        ResponseAPI responseEric = new ObjectMapper().readValue(exampleApiResponseEric, ResponseAPI.class);
        ResponseAPI responseJeff = new ObjectMapper().readValue(exampleApiResponseJeff, ResponseAPI.class);

        Map<String, List<String>> cheapestOptions = new HashMap<>();

        String passengers = "4";

        searchEngine.filterBestPrices("dave", new Supplier("dave").filterOptions(passengers, responseDave.getOptions()), cheapestOptions);
        searchEngine.filterBestPrices("eric", new Supplier("eric").filterOptions(passengers, responseEric.getOptions()), cheapestOptions);
        searchEngine.filterBestPrices("jeff", new Supplier("jeff").filterOptions(passengers, responseJeff.getOptions()), cheapestOptions);

        assertTrue(
                cheapestOptions.size() == 6 &&
                cheapestOptions.get("STANDARD").get(0).equals("jeff") &&
                cheapestOptions.get("EXECUTIVE").get(0).equals("dave") &&
                cheapestOptions.get("LUXURY").get(0).equals("eric") &&
                cheapestOptions.get("PEOPLE_CARRIER").get(0).equals("jeff") &&
                cheapestOptions.get("LUXURY_PEOPLE_CARRIER").get(0).equals("eric") &&
                cheapestOptions.get("MINIBUS").get(0).equals("dave")
                );
    }

    @Test
    public void testResultIsSorted() throws IOException {
        ResponseAPI responseDave = new ObjectMapper().readValue(exampleApiResponseDave, ResponseAPI.class);

        String passengers = "4";

        Map<String, String> options = new Supplier("dave").filterOptions(passengers, responseDave.getOptions());

        Map<String, String> sortedOptions = ConsoleSearchEngine.sortByValue(options);

        List<String> cars = new ArrayList<>(sortedOptions.keySet());

        assertTrue(
                cars.size() == 6 &&
                        cars.get(0).equals("PEOPLE_CARRIER") &&
                        cars.get(1).equals("EXECUTIVE") &&
                        cars.get(2).equals("MINIBUS") &&
                        cars.get(3).equals("LUXURY") &&
                        cars.get(4).equals("STANDARD") &&
                        cars.get(5).equals("LUXURY_PEOPLE_CARRIER")
        );
    }
}