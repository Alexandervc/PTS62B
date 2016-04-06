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
import service.RadService;
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
            service.addRate(1.29, RoadType.A);
            System.out.println("rate-A succeed");
            service.addRate(0.89, RoadType.B);
            System.out.println("rate-B succeed");
            service.addRate(0.49, RoadType.C);
            System.out.println("rate-C succeed");
            service.addRate(0.25, RoadType.D);
            System.out.println("rate-D succeed");
            service.addRate(0.12, RoadType.E);
            System.out.println("rate-E succeed");

            List<RoadUsage> roadUsages = new ArrayList<>();
            RoadUsage usage = new RoadUsage("TestLaan", RoadType.E, 12.9);
            roadUsages.add(usage);

            System.out.println("create person");
            Person p = service.addPerson("Linda", "van Engelen", "LMJC",
                    "Sibeliuslaan", "83B", "5654CV",
                    "Eindhoven", "Nederland");

            System.out.println("create car");
            service.addCar(p, 5L, FuelType.Petrol);
            
            Bill b = new Bill(p, roadUsages, 35.2, 5L, "April", "2016");

            System.out.println("create bill");
            service.addBill(b);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
