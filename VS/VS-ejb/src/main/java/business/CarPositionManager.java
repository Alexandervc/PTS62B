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
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * The manager of carPositions.
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
     * Save the given information into a CarPosition.
     * @param cartrackerId The unique identifier of a cartracker.
     * @param moment The moment in which the cartracker was at the given 
     *      coordinates.
     * @param xCoordinate The x-coordinate of the carPosition.
     * @param yCoordinate The y-coordinate of the carPosition.
     * @param roadName The name of the road on which the cartracker was.
     * @param meter The number of meters the cartracker has measured since 
     *      the last carPosition.
     */
    public void saveCarPosition(String cartrackerId, Date moment, 
            Double xCoordinate, Double yCoordinate, String roadName, 
            Double meter){
        // Find cartracker
        Cartracker cartracker = this.cartrackerDao.find(cartrackerId);
        if(cartracker == null) {
            // TODO foreign cartracker??
            throw new IllegalArgumentException("Cartracker not found");
        }
        
        // TODO road anders
        List<Road> roads = this.roadDao.findAll();
        Random random = new Random();
        Road road = roads.get(random.nextInt(roads.size()));
        
        // Make carPosition
        CarPosition cp = new CarPosition(cartracker, moment, xCoordinate,
            yCoordinate, road, meter);
                
        this.carPositionDao.create(cp);
    }
}
