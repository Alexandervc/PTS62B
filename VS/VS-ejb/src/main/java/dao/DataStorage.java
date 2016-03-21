/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Cartracker;
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
        cartrackerDao.create(new Cartracker(1L));
    }
}
