package common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResponseAPI {

    @JsonProperty("supplier_id")
    private String supplier;

    @JsonProperty("pickup")
    private String pickup;

    @JsonProperty("dropoff")
    private String dropoff;

    @JsonProperty("options")
    private List<Option> options;


    public String getSupplier() {
        return this.supplier;
    }

    public String getPickup() {
        return this.pickup;
    }

    public String getDropOff() {
        return this.dropoff;
    }

    public List<Option> getOptions() {
        return this.options;
    }
}


