package common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Option {

    @JsonProperty("car_type")
    private String carType;

    @JsonProperty("price")
    private String price;

    public String getCarType() {
        return this.carType;
    }

    public String getPrice() {
        return this.price;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
