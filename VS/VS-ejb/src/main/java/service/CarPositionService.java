/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import business.CarPositionManager;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * The service for carpositions.
 * @author Alexander
 */
@Stateless
public class CarPositionService {
    @Inject
    private CarPositionManager carPositionManager;

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
    public void saveCarPosition(Long cartrackerId, Date moment, 
            Double xCoordinate, Double yCoordinate, String roadName, 
            Double meter) {
        this.carPositionManager.saveCarPosition(cartrackerId, moment, xCoordinate, 
                yCoordinate, roadName, meter);
    }
}
