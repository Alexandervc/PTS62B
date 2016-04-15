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

    @PostConstruct
    public void start() {
        if (this.cartrackerDao.find(123456789L) == null) {
            // Insert test data
            Cartracker cartracker = new Cartracker(123456789L);
            this.cartrackerDao.create(cartracker);
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
}