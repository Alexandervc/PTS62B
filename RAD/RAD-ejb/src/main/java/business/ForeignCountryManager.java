/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dao.ForeignCountryRideDao;
import domain.ForeignCountryRide;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * A class which provides functionality regarding foreign countries.
 * @author Jesse
 */
@Stateless
public class ForeignCountryManager {
    @Inject
    private ForeignCountryRideDao foreignCountryRideDao;

    /**
     * Find the ForeignCountryRide by foreignCountryRideId. This uses the
     * ForeignCountryRideDao.
     * @param foreignCountryRideId The id of the foreignCountryRide.
     * @return The ForeignCountryRide with foreignCountryRideId.
     */
    public ForeignCountryRide findRideByForeignCountryRideId(
            Long foreignCountryRideId) {
        
        return this.foreignCountryRideDao.findByForeignCountryRideId(
                foreignCountryRideId);
    }
    
    /**
     * Creates a new ForeignCountryRide in the database.
     * @param foreignCountryRideId The id of the foreign country ride.
     * @param totalPrice The total price of the foreign country ride.
     */
    public void createForeignCountryRide(
            Long foreignCountryRideId, 
            double totalPrice) {
        this.foreignCountryRideDao.create(
                new ForeignCountryRide(foreignCountryRideId, totalPrice));
    }
    
    /**
     * Used for mocking.
     * Gets the ForeignCountryRideDao.
     * @return The ForeignCountryRideDao object.
     */
    public ForeignCountryRideDao getForeignCountryRideDao() {
        return this.foreignCountryRideDao;
    }
    
    /**
     * Used for mocking.
     * Set the ForeignCountryRideDao.
     * @param foreignCountryRideDao The ForeignCountryRideDao object.
     */
    public void setForeignCountryRideDao(
            ForeignCountryRideDao foreignCountryRideDao) {
        this.foreignCountryRideDao = foreignCountryRideDao;
    }    
}
