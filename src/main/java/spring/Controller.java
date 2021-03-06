package spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import common.SearchEngine;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static common.Validator.*;


@RestController
public class Controller {

    /**
     * RestAPI endpoint for search requests. The method uses the SearchEngine class to get all relevant options and
     * Jackson library to convert the Map from the search engine response to a valid JSON.
     * @param supplier the name of the supplier
     * @param pickup the pickup location of the journey
     * @param dropoff the drop off location of the journey
     * @param passengers the number of passengers
     * @return the relevant options as JSON
     */
    @RequestMapping(value = "/json")
    public String getOptions(
            @RequestParam(value = "supplier", required = false) String supplier,
            @RequestParam(value = "pickup", defaultValue = "") String pickup,
            @RequestParam(value = "dropoff", defaultValue = "") String dropoff,
            @RequestParam(value = "passengers", defaultValue = "") String passengers){

        SearchEngine searchEngine = new SearchEngine();
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> suppliers = Arrays.asList("dave", "eric", "jeff");

        String validationMessage = validateArguments(pickup, dropoff, passengers);

        if(!argumentsAreValid(validationMessage)){
            return String.format("%s %n", validationMessage);
        }

        if(supplier != null){
            String validSupplierMessage = validateSupplier(suppliers, supplier);

            if(!argumentsAreValid(validSupplierMessage)){
                return String.format("%s %n", validSupplierMessage);
            }

            Map<String, String> searchResult = searchEngine.singleSupplierSearch(supplier.toLowerCase(), pickup, dropoff, passengers);
            try {
                return String.format("%s %n",  objectMapper.writeValueAsString(searchResult));
            } catch (JsonProcessingException e) {
                return "{\"error\":\"Parsing error occurred.\"}";
            }
        }else{
            Map<String, List<String>> searchResult = searchEngine.multipleSuppliersSearch(pickup, dropoff, passengers, suppliers);
            try {
                return String.format("%s %n", objectMapper.writeValueAsString(searchResult));
            } catch (JsonProcessingException e) {
                return "{\"error\":\"Parsing error occurred.\"}";
            }
        }
    }

}
