/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domain.CarPosition;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import service.jms.SendForeignRideBean;

/**
 * The service for foreign rides.
 * @author Alexander
 */
@Stateless
public class ForeignRideService {
    @Inject
    private SendForeignRideBean sendForeignRideBean;
    
    /**
     * Send the given information to the central queue.
     * @param cartrackerId The foreign cartracker.
     * @param totalPrice The total price of the ride.
     * @param carpositions The carPositions of the ride.
     * @param countryCodeTo The code of the country where the foreign cartracker
     *      is from.
     * @param countryCodeFrom The code of our own country.
     */
    public void sendForeignRide(String cartrackerId, Double totalPrice,
            List<CarPosition> carpositions, String countryCodeTo, 
            String countryCodeFrom) {
        this.sendForeignRideBean.sendForeignRide(cartrackerId, totalPrice, 
                carpositions, countryCodeTo, countryCodeFrom);
    }
}
