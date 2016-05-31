/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Address;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import domain.FuelType;
import domain.Person;
import domain.RoadType;
import dto.BillRoadUsage;
import service.BillService;
import service.CarService;
import service.PersonService;
import service.RateService;

/**
 * Class for test datastorage. Add object of every domain type in db.
 *
 * @author Linda
 */
@Singleton
@Startup
public class DataStorage {
    // Static field for Rate
    private static final double RATE1 = 0.25;
    private static final double RATE2 = 0.20;
    private static final double RATE3 = 0.12;
    private static final double RATE4 = 0.05;
    private static final double RATE5 = 0.02;
    
    @Inject
    private PersonService personService;
    
    @Inject
    private RateService rateService;
    
    @Inject
    private CarService carService;

    /**
     * Method runs at start-up ejb.
     */
    @PostConstruct
    public void onStartup() {
        //if (this.personService.searchPersons("Linda").isEmpty()) {
            // Add rates to db.
            this.rateService.addRate(RATE1, RoadType.A);
            this.rateService.addRate(RATE2, RoadType.B);
            this.rateService.addRate(RATE3, RoadType.C);
            this.rateService.addRate(RATE4, RoadType.D);
            this.rateService.addRate(RATE5, RoadType.E);
            // TODO
            // have to be solved.
            this.rateService.addRate(0.00, RoadType.FOREIGN_COUNTRY_ROAD);

            // Create person in db
            Address address1 = new Address("Calçada do Lavra", "12", 
                    "1150-208", "Lisboa");
            Address address2 = new Address("Calçada do Lavra", "14", 
                    "1150-208", "Lisboa");
            Person p1 = this.personService.addPerson("Linda", "van Engelen", 
                    "LMJC", "Linda1", "admin", address1);
            Person p2 = this.personService.addPerson("Fernando", "Lameirinhas", 
                    "FL", "Fernando1", "admin", address2);
            
            String cartrackerId1 = "PT123456789";
            String cartrackerId2 = "PT112233444";
            String cartrackerId3 = "PT121314151";

            // Create car for person in db.
            this.carService.addCar(p1, cartrackerId1, FuelType.Petrol);
            this.carService.addCar(p2, cartrackerId2, FuelType.Petrol);
            this.carService.addCar(p2, cartrackerId3, FuelType.Diesel);
        //}
    }
}
