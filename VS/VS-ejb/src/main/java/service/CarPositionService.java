/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import business.CarPositionManager;
import domain.CarPosition;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import service.jms.SendForeignRideBean;

/**
 * The service for carpositions.
 * @author Alexander
 */
@Stateless
public class CarPositionService {
    @Inject
    private CarPositionManager carPositionManager;
    
    @Inject
    private SendForeignRideBean sendForeignRideBean;

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
     * @param rideId The id of the ride this carposition is a part of.
     * @param lastOfRide Whether this carposition is the last of 
     *      the ride or not.
     */
    public void processCarPosition(String cartrackerId, Date moment, 
            Double xCoordinate, Double yCoordinate, String roadName, 
            Double meter, Long rideId, Boolean lastOfRide) {
        this.carPositionManager.processCarPosition(cartrackerId, moment, 
                xCoordinate, yCoordinate, roadName, meter, rideId, lastOfRide);
    }
    
    /**
     * Send the given information to the central queue.
     * @param cartrackerId The foreign cartracker.
     * @param totalPrice The total price of the ride.
     * @param carpositions The carPositions of the ride.
     * @param countryCode The code of the country where the foreign cartracker
     *      is from.
     */
    public void sendForeignRide(String cartrackerId, Double totalPrice,
            List<CarPosition> carpositions, String countryCode) {
        this.sendForeignRideBean.sendForeignRide(cartrackerId, totalPrice, 
                carpositions, countryCode);
    }
}
