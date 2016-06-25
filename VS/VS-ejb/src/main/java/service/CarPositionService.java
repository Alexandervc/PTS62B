/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import business.CarPositionManager;
import domain.Coordinate;
import java.util.Date;
import java.util.List;
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
     * @param coordinate The coordinate of this carposition.
     * @param roadName The name of the road on which the cartracker was.
     * @param meter The number of meters the cartracker has measured since
     *      the last carPosition.
     * @param rideId The id of the ride this carposition is a part of.
     * @param foreignCountryRideId THe foreign country ride of this carposition.
     * @param lastOfRide Whether this carposition is the last of 
     *      the ride or not.
     * @param serialNumber serial number from simulator.
     */
    public void processCarPosition(String cartrackerId, Date moment, 
            Coordinate coordinate, String roadName, 
            Double meter, Integer rideId, Long foreignCountryRideId, 
            Boolean lastOfRide, Long serialNumber) {
        this.carPositionManager.processCarPosition(cartrackerId, moment, 
                coordinate, roadName, meter, rideId, foreignCountryRideId, 
                lastOfRide, serialNumber);
    }
    
    /**
     * Get the coordinates in the given month and year for the given 
     *      cartrackerId.
     * @param month The month to get the coordinates for.
     * @param year The year to get the coordinates for.
     * @param cartrackerId The cartracker to get the coordinates for.
     * @return A JSON-string of coordinates.
     */
    public String getCoordinates(int month, int year,
            String cartrackerId) {
        return this.carPositionManager.getCoordinates(month, year, 
                cartrackerId);
    }
}
