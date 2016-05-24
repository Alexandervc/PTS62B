/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import domain.CarPosition;
import dto.Coordinate;

/**
 * The dao for carPosition.
 * @author Alexander
 */
@Stateless
public class CarPositionDao extends AbstractDaoFacade<CarPosition> {
    @PersistenceContext
    private EntityManager em;

    /**
     * The dao for carPosition.
     */
    public CarPositionDao() {
        super(CarPosition.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
    
    /**
     * Get the carpositions for the given month for the given cartracker.
     * @param month The month to get the carpositions for.
     * @param year The year to get the carpositions for.
     * @param cartrackerId The cartracker to get the carpositions for.
     * @return A list of carpositions.
     */
    public List<CarPosition> getPositionsOfMonth(int month, int year, 
            String cartrackerId) {
        Query q = this.em.createNamedQuery("CarPosition.getPositionsOfMonth");
        q.setParameter("month", month);
        q.setParameter("year", year);
        q.setParameter("cartrackerId", cartrackerId);
        return q.getResultList();
    }
    
    /**
     * Get the carpostions for the given rideId.
     * @param rideId The id of the ride to get the carpositions for.
     * @return List of carpostions.
     */
    public List<CarPosition> getPositionsOfRide(Integer rideId) {
        Query q = this.em.createNamedQuery("CarPosition.getPositionsOfRide");
        q.setParameter("rideId", rideId);
        return q.getResultList();
    }
    
    public List<CarPosition> getPositionsOfForeignCountryRide(Long foreignCountryRideId) {
        Query q = this.em.createNamedQuery(
                "CarPosition.getPositionsOfForeignCountryRide");
        q.setParameter("rideId", foreignCountryRideId);
        return q.getResultList();
    }
    
    /**
     * Get the last ride id of a carposition from a countrycode.
     * @param countryCode The country code to sort on. For example: "PT".
     * @return The last ride id.
     */
    public Integer getLastIdOfCountryCode(String countryCode) {
        Query q = this.em
                .createNamedQuery("CarPosition.getLastIdOfCountryCode");
        q.setParameter("countryCode", countryCode + "%");
        
        List<String> result = q.getResultList();
        Integer maxRideId = 0;
        
        for(String rideId : result) {
            Integer parsedRideId = Integer.parseInt(rideId.substring(2)); 
            if (maxRideId < parsedRideId) {
                maxRideId = parsedRideId;
            }
        }
        
        return maxRideId;
    }
    
    /**
     * Get the coordinates in the given month and year for the given 
     *      cartrackerId.
     * @param month The month to get the coordinates for.
     * @param year The year to get the coordinates for.
     * @param cartrackerId The cartracker to get the coordinates for.
     * @return A list of coordinates.
     */
    public List<Coordinate> getCoordinates(int month, int year,
            String cartrackerId) {
        Query q = this.em.createNamedQuery("CarPosition.getCoordinates");
        q.setParameter("month", month);
        q.setParameter("year", year);
        q.setParameter("cartrackerId", cartrackerId);
        
        return q.getResultList();
    }
}
