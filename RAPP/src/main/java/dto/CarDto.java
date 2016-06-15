package dto;

import domain.FuelType;

/**
 * DTO class for Car.
 * @author Melanie
 */
public class CarDto {
    private String cartrackerId;
    private FuelType fuel;
    
    /**
     * Contructor CarDto.
     * @param cartrackerId String.
     * @param fuel type FuelType.
     */
    public CarDto(String cartrackerId, FuelType fuel) {
        this.cartrackerId = cartrackerId;
        this.fuel = fuel;
    }
    
    /**
     * Empty constructor for converting to and from json.
     * @deprecated Only for converting.
     */
    @Deprecated
    public CarDto() {
        // Empty constructor for converting to and from json
    }

    public String getCartrackerId() {
        return this.cartrackerId;
    }

    public void setCartrackerId(String cartrackerId) {
        this.cartrackerId = cartrackerId;
    }

    public FuelType getFuel() {
        return this.fuel;
    }

    public void setFuel(FuelType fuel) {
        this.fuel = fuel;
    }
}
