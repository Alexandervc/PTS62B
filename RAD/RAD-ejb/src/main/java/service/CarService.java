/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import business.CarManager;
import domain.Car;
import domain.FuelType;
import domain.Person;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Service for car.
 * @author Alexander
 */
@Stateless
public class CarService {
    @Inject
    private CarManager carManager;
    
    /**
     * Add car to database.
     *
     * @param person otype Person.
     * @param cartracker id Long.
     * @param fuel fueltype.
     */
    public void addCar(Person person, String cartracker, FuelType fuel) {
        this.carManager.createCar(person, cartracker, fuel);
    }
    
    /**
     * Gets the car with a given cartrackerId.
     * @param cartrackerId The unique cartrackerId.
     * @return The car with the unique cartrackerId.
     */
    public Car getCar(String cartrackerId) {
        return this.carManager.getCar(cartrackerId);
    }
}
