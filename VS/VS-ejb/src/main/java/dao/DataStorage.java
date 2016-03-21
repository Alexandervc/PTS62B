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
        if (cartrackerDao.find(1L) == null) {
            Cartracker cartracker = new Cartracker(1L);
            cartrackerDao.create(cartracker);
            Road road = new Road("A1", RoadType.A);
            roadDao.create(road);
            CarPosition cp = new CarPosition(cartracker, new Date(), 1.0, 1.0, road, 1.0);
            carPositionDao.create(cp);
        }
    }
}
