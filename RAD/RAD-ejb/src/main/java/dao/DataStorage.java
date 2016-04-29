/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Bill;
import domain.FuelType;
import domain.Person;
import domain.RoadType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import service.RadService;
import service.RoadUsage;

/**
 * Class for test datastorage. Add object of every domain type in db.
 *
 * @author Linda.
 */
@Singleton
@Startup
public class DataStorage {

    private static final Logger LOGGER = Logger
            .getLogger(DataStorage.class.getName());

    // Static field for Rate
    private static final double RATE1 = 1.29;
    private static final double RATE2 = 0.89;
    private static final double RATE3 = 0.49;
    private static final double RATE4 = 0.25;
    private static final double RATE5 = 0.12;
    
    // static field for roadusage km
    private static final double KM = 12.9;
    
    // static field for cartracker id
    private static final String CARTRACKERID = "PT123456789";
    
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
            LOGGER.log(Level.INFO, "rate-A succeed");
            this.service.addRate(RATE2, RoadType.B);
            LOGGER.log(Level.INFO, "rate-B succeed");
            this.service.addRate(RATE3, RoadType.C);
            LOGGER.log(Level.INFO, "rate-C succeed");
            this.service.addRate(RATE4, RoadType.D);
            LOGGER.log(Level.INFO, "rate-D succeed");
            this.service.addRate(RATE5, RoadType.E);
            LOGGER.log(Level.INFO, "rate-E succeed");

            // Make list of roadusages
            List<RoadUsage> roadUsages = new ArrayList<>();
            RoadUsage usage = new RoadUsage("TestLaan", RoadType.E, KM);
            roadUsages.add(usage);

            // Create person in db
            Person p = this.service.addPerson("Linda", "van Engelen", "LMJC",
                    "Sibeliuslaan", "83B", "5654CV",
                    "Eindhoven", "Nederland");
            LOGGER.log(Level.INFO, "created person");

            // Create car for person in db.
            this.service.addCar(p, CARTRACKERID, FuelType.Petrol);
            LOGGER.log(Level.INFO, "created car");
            
            // Create bill for person.
            Bill b = new Bill(p, roadUsages, PRICE, CARTRACKERID, 
                    "April", "2016");
            
            // Add bill to db.
            this.service.addBill(b);
            LOGGER.log(Level.INFO, "add bill in db");
        }
    }
}
