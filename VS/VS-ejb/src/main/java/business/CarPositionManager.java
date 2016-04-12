/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dao.CarPositionDao;
import dao.CartrackerDao;
import dao.RoadDao;
import domain.CarPosition;
import domain.Cartracker;
import domain.Road;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Alexander
 */
@Stateless
public class CarPositionManager {
    @Inject
    private CartrackerDao cartrackerDao;
    
    @Inject
    private RoadDao roadDao;
    
    @Inject
    private CarPositionDao carPositionDao;
    
    /**
     * Save the given information in a CarPosition
     * @param cartrackerId
     * @param moment
     * @param xCoordinate
     * @param yCoordinate
     * @param roadName
     * @param meter 
     */
    public void saveCarPosition(Long cartrackerId, Date moment, 
            Double xCoordinate, Double yCoordinate, String roadName, 
            Double meter){
        // find cartracker
        Cartracker cartracker = this.cartrackerDao.find(cartrackerId);
        if(cartracker == null) {
            // TODO foreign cartracker
            throw new IllegalArgumentException("Cartracker not found");
        }
        
        // TODO road anders
        List<Road> roads = roadDao.findAll();
        Random random = new Random();
        Road road = roads.get(random.nextInt(roads.size()));
        
        CarPosition cp = new CarPosition(cartracker, moment, xCoordinate,
            yCoordinate, road, meter);
        Logger.getLogger(CarPositionManager.class.getName())
                .log(Level.INFO, cp.toString());
                
        this.carPositionDao.create(cp);
    }
}
