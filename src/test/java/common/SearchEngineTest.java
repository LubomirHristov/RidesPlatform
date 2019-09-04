package common;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class SearchEngineTest {
    private Map<String, Integer> carTypesMap = new HashMap<String, Integer>(){{
        put("STANDARD", 4);
        put("EXECUTIVE", 4);
        put("LUXURY", 4);
        put("PEOPLE_CARRIER", 6);
        put("LUXURY_PEOPLE_CARRIER", 6);
        put("MINIBUS", 16);
    }};

    private SearchEngine searchEngine = new SearchEngine();

    @Test
    public void areOnlyRelevantOptionsShown(){
        String passengers = "6";

        List<Option> allOptions = new ArrayList<>();

        Option option1 = new Option();
        option1.setCarType("PEOPLE_CARRIER");
        option1.setPrice("4");
        Option option2 = new Option();
        option2.setCarType("STANDARD");
        option2.setPrice("14");
        Option option3 = new Option();
        option3.setCarType("EXECUTIVE");
        option3.setPrice("27");
        Option option4 = new Option();
        option4.setCarType("MINIBUS");
        option4.setPrice("44");
        Option option5 = new Option();
        option5.setCarType("LUXURY_PEOPLE_CARRIER");
        option5.setPrice("57");
        Option option6 = new Option();
        option6.setCarType("LUXURY");
        option6.setPrice("100");

        allOptions.add(option1);
        allOptions.add(option2);
        allOptions.add(option3);
        allOptions.add(option4);
        allOptions.add(option5);
        allOptions.add(option6);

        Map<String, String> filteredOptions = new HashMap<String, String>(){{
            put("PEOPLE_CARRIER", "4");
            put("MINIBUS", "44");
            put("LUXURY_PEOPLE_CARRIER", "57");
        }};

        boolean allRelevantOptionsArePresent = true;

        for(Option option: allOptions){
            if(carTypesMap.get(option.getCarType()) > Integer.parseInt(passengers) && !filteredOptions.containsKey(option.getCarType())){
                allRelevantOptionsArePresent = false;
            }
        }

        assertTrue(allRelevantOptionsArePresent);
    }

    @Test
    public void isOnlyTheBestPriceForACarTypeShown(){
        List<String> suppliers = Arrays.asList("dave", "eric", "jeff");

        List<Map<String, String>> allSuppliersOptions = new ArrayList<>();

        Map<String, String> daveOptions = new HashMap<String, String>(){{
            put("PEOPLE_CARRIER", "4");
            put("STANDARD", "14");
            put("EXECUTIVE", "27");
            put("MINIBUS", "44");
            put("LUXURY_PEOPLE_CARRIER", "57");
            put("LUXURY", "100");
        }};
        Map<String, String> ericOptions = new HashMap<String, String>(){{
            put("PEOPLE_CARRIER", "14");
            put("STANDARD", "12");
            put("EXECUTIVE", "31");
            put("MINIBUS", "55");
            put("LUXURY_PEOPLE_CARRIER", "52");
            put("LUXURY", "110");
        }};
        Map<String, String> jeffOptions = new HashMap<String, String>(){{
            put("PEOPLE_CARRIER", "24");
            put("STANDARD", "22");
            put("EXECUTIVE", "25");
            put("MINIBUS", "66");
            put("LUXURY_PEOPLE_CARRIER", "60");
            put("LUXURY", "99");
        }};

        allSuppliersOptions.add(daveOptions);
        allSuppliersOptions.add(ericOptions);
        allSuppliersOptions.add(jeffOptions);

        Map<String, List<String>> cheapestOptions = new HashMap<>();

        for(int i = 0; i < 3; i++){
            searchEngine.filterBestPrices(suppliers.get(i), allSuppliersOptions.get(i), cheapestOptions);
        }

        boolean onlyBestPricesAreShown = true;

        for(String carType: cheapestOptions.keySet()){
            int price = Integer.parseInt(cheapestOptions.get(carType).get(1));

            if(daveOptions.containsKey(carType) && Integer.parseInt(daveOptions.get(carType)) < price){
                onlyBestPricesAreShown = false;
                break;
            }

            if(ericOptions.containsKey(carType) && Integer.parseInt(ericOptions.get(carType)) < price){
                onlyBestPricesAreShown = false;
                break;
            }

            if(jeffOptions.containsKey(carType) && Integer.parseInt(jeffOptions.get(carType)) < price){
                onlyBestPricesAreShown = false;
                break;
            }
        }

        assertTrue(onlyBestPricesAreShown);
    }
}