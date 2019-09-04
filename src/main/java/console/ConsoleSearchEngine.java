package console;

import common.SearchEngine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.LinkedHashMap;


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

        if(!validationMessage.equals("")){
            System.out.println(validationMessage);
            System.exit(0);
        }

        // if a supplier has been passed as a commandline argument
        if(numOfArguments == 4) {
            String supplier = args[3].toLowerCase();
            String validSupplierMessage = validateSupplier(suppliers, supplier);

            if(!validSupplierMessage.equals("")){
                System.out.println(validSupplierMessage);
                System.exit(0);
            }

            printSingleSupplierOptions(searchEngine.singleSupplierSearch(supplier, pickup, dropOff, passengers), passengers);
        }else{
            printBestPrices(searchEngine.multipleSuppliersSearch(pickup, dropOff, passengers, suppliers));
        }
    }

    /**
     * Validates the pickup, drop off and passengers commandline arguments
     * @param pickup pickup location of the journey
     * @param dropOff drop off location of the journey
     * @param passengers number of passengers
     * @return relevant validation message
     */
    public static String validateArguments(String pickup, String dropOff, String passengers){
        String[] pickupCoordinates = pickup.split(",");
        String[] dropOffCoordinates = dropOff.split(",");

        if(pickupCoordinates.length != 2 || dropOffCoordinates.length != 2){
            return "Invalid coordinates.";
        }

        try{
            Double.parseDouble(pickupCoordinates[0]);
            Double.parseDouble(pickupCoordinates[1]);
            Double.parseDouble(dropOffCoordinates[0]);
            Double.parseDouble(dropOffCoordinates[1]);
        }catch (NumberFormatException nfe){
            return "Invalid coordinates.";
        }

        try{
            int numOfPassengers = Integer.parseInt(passengers);
            if(numOfPassengers < 0){
                throw new NumberFormatException();
            }
        }catch (NumberFormatException nfe){
            return "Invalid number of passengers.";
        }

        return "";
    }

    /**
     * Validates the supplier commandline argument
     * @param availableSuppliers list of available suppliers
     * @param supplier supplier that is checked
     * @return relevant validation message
     */
    public static String validateSupplier(List<String> availableSuppliers, String supplier){
        if(availableSuppliers.contains(supplier)){
            return "";
        }

        return String.format("Supplier %s does not exist.", supplier);
    }

    /**
     * Prints all options form a single supplier in the {car type} - {price} format
     * @param options the options that the supplier offers
     * @param passengers the number of passengers
     */
    public static void printSingleSupplierOptions(Map<String, String> options, String passengers){
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
    private static Map<String, String> sortByValue(Map<String, String> unsortedOptions) {
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
