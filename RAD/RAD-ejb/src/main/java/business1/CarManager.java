/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business1;

import dao1.CarDAO;
import domain1.Car;
import domain1.FuelType;
import domain1.Person;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Linda.
 */
@Stateless
public class CarManager {
    
    @Inject
    private CarDAO carDAO;
    
    /**
     * Create car in Database.
     * @param person Type Person.
     * @param cartracker Long.
     * @param fuel Type FuelType.
     */
    public void createCar(Person person, Long cartracker, FuelType fuel){
        Car car = new Car(person, cartracker, fuel);
        carDAO.create(car);
    }
}
