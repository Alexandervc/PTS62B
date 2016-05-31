package dto;

import domain.FuelType;

/**
 * DTO class for Car.
 * @author Melanie
 */
public class CarDto {
    private Long cartrackerId;
    private FuelType fuel;
    
    public CarDto(Long cartrackerId, FuelType fuel) {
        this.cartrackerId = cartrackerId;
        this.fuel = fuel;
    }

    public Long getCartrackerId() {
        return cartrackerId;
    }

    public void setCartrackerId(Long cartrackerId) {
        this.cartrackerId = cartrackerId;
    }

    public FuelType getFuel() {
        return fuel;
    }

    public void setFuel(FuelType fuel) {
        this.fuel = fuel;
    }
}
