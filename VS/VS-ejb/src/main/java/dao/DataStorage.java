/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.CarPosition;
import domain.Cartracker;
import domain.Road;
import domain.RoadType;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 * @author Alexander
 */
@Singleton
@Startup
public class DataStorage {

    @Inject
    private CarPositionDao carPositionDao;

    @Inject
    private CartrackerDao cartrackerDao;

    @Inject
    private RoadDao roadDao;

    @PostConstruct
    public void start() {
        System.out.println("start datastorage");
        if (cartrackerDao.find(123456789L) == null) {
            Cartracker cartracker = new Cartracker(123456789L);
            cartrackerDao.create(cartracker);
            Road road = new Road("A1", RoadType.A);
            Road road2 = new Road("B1", RoadType.B);
            Road road3 = new Road("C1", RoadType.C);
            Road road4 = new Road("D1", RoadType.D);
            Road road5 = new Road("E1", RoadType.E);
            roadDao.create(road);
            roadDao.create(road2);
            roadDao.create(road3);
            roadDao.create(road4);
            roadDao.create(road5);
            CarPosition cp = new CarPosition(cartracker, new Date(), 1.0,
                    1.0, road, 1.0);
            CarPosition cp2 = new CarPosition(cartracker, new Date(), 2.0,
                    2.0, road2, 2.0);
            CarPosition cp3 = new CarPosition(cartracker, new Date(), 3.0, 
                    3.0, road3, 3.0);
            CarPosition cp4 = new CarPosition(cartracker, new Date(), 4.0, 
                    4.0, road4, 4.0);
            CarPosition cp5 = new CarPosition(cartracker, new Date(), 5.0, 
                    5.0, road5, 5.0);
            CarPosition cp6 = new CarPosition(cartracker, new Date(), 10.0, 
                    10.0, road, 10.0);
            CarPosition cp7 = new CarPosition(cartracker, new Date(), 11.0, 
                    11.0, road, 11.0);
            CarPosition cp8 = new CarPosition(cartracker, new Date(), 12.0, 
                    12.0, road, 12.0);
            carPositionDao.create(cp);
            carPositionDao.create(cp2);
            carPositionDao.create(cp3);
            carPositionDao.create(cp4);
            carPositionDao.create(cp5);
            carPositionDao.create(cp6);
            carPositionDao.create(cp7);
            carPositionDao.create(cp8);
            
        }
    }
}
