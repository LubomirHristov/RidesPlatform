package console;

import common.SearchEngine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.LinkedHashMap;

import static common.Validator.*;


public class ConsoleSearchEngine {

    /**
     * The method that starts the console search engine
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SearchEngine searchEngine = new SearchEngine();
        List<String> suppliers = Arrays.asList("dave", "eric", "jeff");
        int numOfArguments = args.length;

        String pickup = args[0];
        String dropOff = args[1];
        String passengers = args[2];

        String validationMessage = validateArguments(pickup, dropOff, passengers);

        if(!argumentsAreValid(validationMessage)){
            System.out.println(validationMessage);
            System.exit(0);
        }

        // if a supplier has been passed as a commandline argument
        if(numOfArguments == 4) {
            String supplier = args[3].toLowerCase();
            String validSupplierMessage = validateSupplier(suppliers, supplier);

            if(!argumentsAreValid(validSupplierMessage)){
                System.out.println(validSupplierMessage);
                System.exit(0);
            }

            printSingleSupplierOptions(searchEngine.singleSupplierSearch(supplier, pickup, dropOff, passengers), passengers);
        }else{
            printBestPrices(searchEngine.multipleSuppliersSearch(pickup, dropOff, passengers, suppliers));
        }
    }

    /**
     * Prints all options form a single supplier in the {car type} - {price} format
     * @param options the options that the supplier offers
     * @param passengers the number of passengers
     */
    private static void printSingleSupplierOptions(Map<String, String> options, String passengers){
        Map<String, String> sortedOptions = sortByValue(options);

        if(options.size() == 0){
            System.out.println(String.format("No car available for %s passengers.", passengers));
        }else {
            for (String carType : sortedOptions.keySet()) {
                String price = options.get(carType);

                System.out.println(String.format("%s - %s", carType, price));
            }
        }
    }

    /***
     * Sorts the given map by the value, which represents an integer, in descending order
     * @param unsortedOptions an unsorted map of options
     * @return a sorted map with all options
     */
    public static Map<String, String> sortByValue(Map<String, String> unsortedOptions) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, String>> list =
                new LinkedList<>(unsortedOptions.entrySet());

        // Sort the list
        list.sort((o1, o2) -> Integer.valueOf(o2.getValue()).compareTo(Integer.valueOf(o1.getValue())));

        // Put data from sorted list to hashmap
        HashMap<String, String> temp = new LinkedHashMap<>();
        for (Map.Entry<String, String> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }

        return temp;
    }

    /***
     * Prints the best price and supplier for each available car type
     * @param cheapestOptions map with the cheapest option from every supplier
     */
    private static void printBestPrices(Map<String, List<String>> cheapestOptions) {
        for (String carType: cheapestOptions.keySet()){
            String supplier = cheapestOptions.get(carType).get(0);
            String price = cheapestOptions.get(carType).get(1);

            System.out.println( String.format("%s - %s - %s", carType, supplier, price));
        }
    }
}
