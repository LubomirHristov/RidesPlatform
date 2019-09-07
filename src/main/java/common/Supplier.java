package common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Supplier {
    private String name;
    private HashMap<String, Integer> carTypesMap;

    /***
     * The constructor for the supplier. Instantiates the map of car types available.
     * @param name the name of the supplier
     */
    public Supplier(String name){
        this.name = name;
        this.carTypesMap = new HashMap<>();
        this.carTypesMap.put("STANDARD", 4);
        this.carTypesMap.put("EXECUTIVE", 4);
        this.carTypesMap.put("LUXURY", 4);
        this.carTypesMap.put("PEOPLE_CARRIER", 6);
        this.carTypesMap.put("LUXURY_PEOPLE_CARRIER", 6);
        this.carTypesMap.put("MINIBUS", 16);
    }

    /**
     * Uses the HttpRequest class to make a request and Jackson library to transform the API response to a java class.
     * It filters the response by removing irrelevant options.
     * @param pickup pickup location of the journey
     * @param dropOff drop off location of the journey
     * @param passengers the number of passengers
     * @return map from car type to price, including only the relevant options
     */
    public Map<String, String> getOptions(String pickup, String dropOff, String passengers){
        HttpRequest request = new HttpRequest();
        List<Option> allOptions;
        Map<String, String> filteredOptions;
        String jsonString;
        ResponseAPI response;

        try {
            jsonString = request.makeRequest(this.name, pickup, dropOff);
            response = new ObjectMapper().readValue(jsonString, ResponseAPI.class);
        } catch (IOException e) {
            System.out.println(String.format("Could not reach supplier %s. Skipping...", this.name));
            return new HashMap<>();
        }

        allOptions = response.getOptions();

        filteredOptions = filterOptions(passengers, allOptions);

        return filteredOptions;
    }

    /**
     * Filters the given options by removing the cars with less number of seats than the required
     * @param passengers the number of passengers
     * @param allOptions the unfiltered options
     * @return the relevant map of options
     */
    public Map<String, String> filterOptions(String passengers, List<Option> allOptions) {
        Map<String, String> filteredOptionsMap = new HashMap<>();

        for (Option option : allOptions) {
            String carType = option.getCarType();
            String price = option.getPrice();

            // Include only if there is a car with enough capacity
            if (Integer.parseInt(passengers) <= this.carTypesMap.get(carType)) {
                filteredOptionsMap.put(carType, price);
            }
        }

        return filteredOptionsMap;
    }


}
