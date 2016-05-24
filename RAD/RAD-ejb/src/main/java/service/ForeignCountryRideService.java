/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import business.ForeignCountryManager;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Service for foreignCountryRide.
 * @author Alexander
 */
@Stateless
public class ForeignCountryRideService {
    @Inject
    private ForeignCountryManager foreignCountryManager;
    
    /**
     * Add a foreign country ride to the database, this stores the total price
     * of the ride.
     *
     * @param foreignCountryRideId The id of the foreign country ride, this id
     *      is set in VS when the message is received from the central system.
     * @param totalPrice The total price of the foreign country ride.
     */
    public void addForeignCountryRide(
            Long foreignCountryRideId,
            double totalPrice) {
        this.foreignCountryManager.createForeignCountryRide(
                foreignCountryRideId,
                totalPrice);
    }
}
