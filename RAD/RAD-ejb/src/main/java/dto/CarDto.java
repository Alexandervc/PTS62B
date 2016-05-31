package dto;

import domain.FuelType;

/**
 * DTO class for Car.
 * @author Melanie
 */
public class CarDto {
    private String cartrackerId;
    private FuelType fuel;
    
    public CarDto(String cartrackerId, FuelType fuel) {
        this.cartrackerId = cartrackerId;
        this.fuel = fuel;
    }

    public String getCartrackerId() {
        return cartrackerId;
    }

    public void setCartrackerId(String cartrackerId) {
        this.cartrackerId = cartrackerId;
    }

    public FuelType getFuel() {
        return fuel;
    }

    public void setFuel(FuelType fuel) {
        this.fuel = fuel;
    }
}
