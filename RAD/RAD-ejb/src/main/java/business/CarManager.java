/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dao.CarDao;
import domain.Car;
import domain.FuelType;
import domain.Person;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Manager for CarDao.
 * @author Linda.
 */
@Stateless
public class CarManager {
    
    @Inject
    private CarDao carDAO;
    
    /**
     * Create car in Database.
     * @param person Type Person.
     * @param cartracker Long.
     * @param fuel Type FuelType.
     */
    public void createCar(Person person, Long cartracker, FuelType fuel){
        Car car = new Car(person, cartracker, fuel);
        this.carDAO.create(car);
    }
}
