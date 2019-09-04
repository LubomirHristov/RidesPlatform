package common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchEngine {

    /**
     * Makes an API call to a single supplier
     * @param supplierName name of the supplier
     * @param pickup pickup location of the journey
     * @param dropOff drop off location of the journey
     * @param passengers number of passengers
     * @return a map with all available options of the supplier
     */
    public Map<String, String> singleSupplierSearch(String supplierName, String pickup, String dropOff, String passengers) {
        Supplier supplier = new Supplier(supplierName);
        return supplier.getOptions(pickup, dropOff, passengers);
    }

    /**
     * Makes an API call to all available suppliers
     * @param pickup pickup location of the journey
     * @param dropOff drop off location of the journey
     * @param passengers number of passengers
     * @param suppliers the list of all available suppliers
     * @return a map with the cheapest car options from all suppliers
     */
    public Map<String, List<String>> multipleSuppliersSearch(String pickup, String dropOff, String passengers, List<String> suppliers) {
        // a mapping from a supplier to ["car type", "price"]
        Map<String, List<String>> cheapestOptions = new HashMap<>();

        for (String supplierName: suppliers){
            Supplier supplier = new Supplier(supplierName);
            Map<String, String> options = supplier.getOptions(pickup, dropOff, passengers);

            // if the current supplier has a better price for a car -> update the map
            filterBestPrices(supplierName, options, cheapestOptions);
        }

        return cheapestOptions;
    }

    /**
     * Updates the current best options if there is a better price for a car offered by the given supplier
     * @param supplierName the current supplier
     * @param currentOptions the current options
     * @param cheapestOptions the updated options that contain the cheapest prices only
     */
    public void filterBestPrices(String supplierName, Map<String, String> currentOptions, Map<String, List<String>> cheapestOptions) {
        for(String carType: currentOptions.keySet()){
            String price = currentOptions.get(carType);

            if(cheapestOptions.containsKey(carType)){
                List<String> supplierAndPriceList = cheapestOptions.get(carType);
                String currentCheapestPrice = supplierAndPriceList.get(1);

                if(Integer.parseInt(currentCheapestPrice) > Integer.parseInt(price)){
                    List<String> newSupplierAndPriceList = Arrays.asList(supplierName, price);
                    cheapestOptions.put(carType, newSupplierAndPriceList);
                }
            }else{
                List<String> supplierAndPriceList = Arrays.asList(supplierName, price);
                cheapestOptions.put(carType,supplierAndPriceList);
            }
        }
    }
}
