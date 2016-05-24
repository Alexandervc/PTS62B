/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dto.Coordinate;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import service.rest.clients.CoordinatesClient;

/**
 * Service for carPosition.
 * @author Alexander
 */
@Stateless
public class CarPositionService {
    @Inject
    private CoordinatesClient coordinatesClient;
    
    /**
     * Get the coordinates in the given month and year for the given 
     *      cartrackerId.
     * @param month The month to get the coordinates for.
     * @param year The year to get the coordinates for.
     * @param cartrackerId The cartracker to get the coordinates for.
     * @return List of coordinates.
     */
    public List<Coordinate> getCoordinates(String cartrackerId, 
            int month, int year) {
        return this.coordinatesClient.getCoordinates(cartrackerId, month, year);
    }
}
