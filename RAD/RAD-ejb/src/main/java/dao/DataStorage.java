/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import domain.Bill;
import domain.FuelType;
import domain.Person;
import domain.RoadType;
import dto.RoadUsage;
import service.RadService;

/**
 * Class for test datastorage. Add object of every domain type in db.
 *
 * @author Linda
 */
@Singleton
@Startup
public class DataStorage {
    // Static field for Rate
    private static final double RATE1 = 1.29;
    private static final double RATE2 = 0.89;
    private static final double RATE3 = 0.49;
    private static final double RATE4 = 0.25;
    private static final double RATE5 = 0.12;
    
    // static field for roadusage km
    private static final double KM = 12.9;
    
    // static field for total price bill
    private static final double PRICE = 35.2;
    @Inject
    private RadService service;

    /**
     * Method runs at start-up ejb.
     */
    @PostConstruct
    public void onStartup() {
        if (this.service.findPersonByName("Linda") == null) {
            // Add rates to db.
            this.service.addRate(RATE1, RoadType.A);
            this.service.addRate(RATE2, RoadType.B);
            this.service.addRate(RATE3, RoadType.C);
            this.service.addRate(RATE4, RoadType.D);
            this.service.addRate(RATE5, RoadType.E);

            // Make list of roadusages
            List<RoadUsage> roadUsages = new ArrayList<>();
            RoadUsage usage = new RoadUsage("TestLaan", RoadType.E, KM);
            roadUsages.add(usage);

            // Create person in db
            Person p1 = this.service.addPerson("Linda", "van Engelen", "LMJC",
                    "Calçada do Lavra", "12", "1150-208",
                    "Lisboa", "Portugal");
            Person p2 = this.service.addPerson("Fernando", "Lameirinhas", "FL",
                    "Calçada do Lavra", "14", "1150-208",
                    "Lisboa", "Portugal");
            
            String cartrackerId1 = "PT123456789";
            String cartrackerId2 = "PT112233444";
            String cartrackerId3 = "PT121314151";

            // Create car for person in db.
            this.service.addCar(p1, cartrackerId1, FuelType.Petrol);
            this.service.addCar(p2, cartrackerId2, FuelType.Petrol);
            this.service.addCar(p2, cartrackerId3, FuelType.Diesel);
            
            // Create bill for person.
            Bill b = new Bill(p1, roadUsages, PRICE, cartrackerId1, 
                    "April", "2016");
            
            // Add bill to db.
            this.service.addBill(b);
        }
    }
}
