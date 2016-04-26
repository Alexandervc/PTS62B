/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import service.RoadUsage;
import domain.Bill;
import domain.FuelType;
import domain.Person;
import domain.RoadType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.RadService;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * Class for test datastorage.
 * @author Linda.
 */
@Singleton
@Startup
public class DataStorage {

    @Inject
    private RadService service;

    @PostConstruct
    public void onStartup() {
        if (service.findPersonByName("Linda") == null) {
            service.addRate(1.29, RoadType.A);
            Logger.getLogger(DataStorage.class.getName())
                    .log(Level.INFO, "rate-A succeed");
            service.addRate(0.89, RoadType.B);
            Logger.getLogger(DataStorage.class.getName())
                    .log(Level.INFO, "rate-B succeed");
            service.addRate(0.49, RoadType.C);
            Logger.getLogger(DataStorage.class.getName())
                    .log(Level.INFO, "rate-C succeed");
            service.addRate(0.25, RoadType.D);
            Logger.getLogger(DataStorage.class.getName())
                    .log(Level.INFO, "rate-D succeed");
            service.addRate(0.12, RoadType.E);
            Logger.getLogger(DataStorage.class.getName())
                    .log(Level.INFO, "rate-E succeed");

            List<RoadUsage> roadUsages = new ArrayList<>();
            RoadUsage usage = new RoadUsage("TestLaan", RoadType.E, 12.9);
            roadUsages.add(usage);

            Logger.getLogger(DataStorage.class.getName())
                    .log(Level.INFO, "create person");
            Person p = service.addPerson("Linda", "van Engelen", "LMJC",
                    "Sibeliuslaan", "83B", "5654CV",
                    "Eindhoven", "Nederland");

            Logger.getLogger(DataStorage.class.getName())
                    .log(Level.INFO, "create car");
            service.addCar(p, "PT123456789", FuelType.Petrol);

            Bill b = new Bill(p, roadUsages, 35.2, "PT123456789", "April", "2016");

            Logger.getLogger(DataStorage.class.getName())
                    .log(Level.INFO, "create bill");
            service.addBill(b);
        }
    }
}
