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
import domain.Coordinate;
import java.util.Calendar;
import java.util.Date;

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
     * Get the carpostions for the given rideId.
     * @param rideId The id of the ride to get the carpositions for.
     * @return List of carpostions.
     */
    public List<CarPosition> getPositionsOfRide(Integer rideId) {
        Query q = this.em.createNamedQuery("CarPosition.getPositionsOfRide");
        q.setParameter("rideId", rideId);
        return q.getResultList();
    }
    
    public List<CarPosition> getPositionsOfForeignCountryRide(
            Long foreignCountryRideId) {
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
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE)); // changed calendar to cal
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
        Date beginDate = cal.getTime();

        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE)); // changed calendar to cal
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));

        
        Date endDate = cal.getTime();
        
        System.out.println(beginDate);
        System.out.println(endDate);
        Query q = this.em.createNamedQuery("CarPosition.getCoordinates");
        q.setParameter("beginDate", beginDate);
        q.setParameter("endDate", endDate);
        q.setParameter("cartrackerId", cartrackerId);
        
        List<Coordinate> returnValue = q.getResultList();
        System.out.println(new Date().getTime() - date.getTime());
        return returnValue;
    }
}
