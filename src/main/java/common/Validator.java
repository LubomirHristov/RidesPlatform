package common;

import java.util.List;

public class Validator {
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
     * Checks if the supplied arguments are valid
     * @param validationMessage the validation message
     * @return validity of arguments
     */
    public static boolean argumentsAreValid(String validationMessage){
        return validationMessage.equals("");
    }
}
