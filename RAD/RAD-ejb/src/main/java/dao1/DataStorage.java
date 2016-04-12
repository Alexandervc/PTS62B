/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao1;

import service1.RoadUsage;
import domain1.Bill;
import domain1.FuelType;
import domain1.Person;
import domain1.RoadType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import service1.RadService;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 * @author Linda
 */
@Singleton
@Startup
public class DataStorage {

    @Inject
    private RadService service;

    @PostConstruct
    public void onStartup() {
        try {
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
                RoadUsage usage1 = new RoadUsage("TestLaan", RoadType.E, 12.9);
                RoadUsage usage2 = new RoadUsage("TestLaan", RoadType.A, 5.9);
                RoadUsage usage3 = new RoadUsage("TestLaan", RoadType.C, 8.4);
                RoadUsage usage4 = new RoadUsage("TestLaan", RoadType.B, 4.2);
                roadUsages.add(usage1);
                roadUsages.add(usage2);
                roadUsages.add(usage3);
                roadUsages.add(usage4);

                Logger.getLogger(DataStorage.class.getName())
                        .log(Level.INFO, "create person");
                Person p = service.addPerson("Linda", "van Engelen", "LMJC",
                        "Sibeliuslaan", "83B", "5654CV",
                        "Eindhoven", "Nederland");

                Logger.getLogger(DataStorage.class.getName())
                        .log(Level.INFO, "create car");
                service.addCar(p, 123456789L, FuelType.Petrol);

                Bill b = new Bill(p, roadUsages, 35.2, 123456789L, "April", "2016");

                Logger.getLogger(DataStorage.class.getName())
                        .log(Level.INFO, "create bill");
                service.addBill(b);
            }

        } catch (Exception e) {
            Logger.getLogger(DataStorage.class.getName())
                    .log(Level.SEVERE, null, e);
        }
    }
}
