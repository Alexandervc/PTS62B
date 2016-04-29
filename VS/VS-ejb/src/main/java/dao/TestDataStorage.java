/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Cartracker;
import domain.Road;
import domain.RoadType;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * Class which handles inserting test data.
 * @author Alexander
 */
@Singleton
@Startup
public class TestDataStorage {
    @Inject
    private CarPositionDao carPositionDao;

    @Inject
    private CartrackerDao cartrackerDao;

    @Inject
    private RoadDao roadDao;

    /**
     * Save testdata.
     */
    @PostConstruct
    public void start() {
        String cartrackerId1 = "PT123456789";
        if (this.cartrackerDao.find(cartrackerId1) == null) {
            // Insert test data
            Cartracker cartracker = new Cartracker(cartrackerId1);
            this.cartrackerDao.create(cartracker);            
        }
        
        String cartrackerId2 = "BE-a5eff926-e3f7-43d5-b62b-5140aa2b962f";
        if (this.cartrackerDao.find(cartrackerId2) == null) {
            // Insert test data
            Cartracker cartracker = new Cartracker(cartrackerId2);
            this.cartrackerDao.create(cartracker);
        }        
        
        String cartrackerId3 = "NL123456789";
        if (this.cartrackerDao.find(cartrackerId3) == null) {
            // Insert test data
            Cartracker cartracker = new Cartracker(cartrackerId3);
            this.cartrackerDao.create(cartracker);
        }
        
        Road road = new Road("A1", RoadType.A);
        Road road2 = new Road("B1", RoadType.B);
        Road road3 = new Road("C1", RoadType.C);
        Road road4 = new Road("D1", RoadType.D);
        Road road5 = new Road("E1", RoadType.E);
        this.roadDao.create(road);
        this.roadDao.create(road2);
        this.roadDao.create(road3);
        this.roadDao.create(road4);
        this.roadDao.create(road5);
    }
}
