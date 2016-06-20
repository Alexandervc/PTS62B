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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

/**
 * The dao for carPosition.
 *
 * @author Alexander
 */
@Stateless
public class CarPositionDao extends AbstractDaoFacade<CarPosition> {

    private static final Logger LOGGER
            = Logger.getLogger(CarPositionDao.class.getName());

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
     *
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
     *
     * @param rideId The id of the ride to get the carpositions for.
     * @param cartracker id of cartracker.
     * @return List of carpostions.
     */
    public List<CarPosition> getPositionsOfRide(Integer rideId, 
            String cartracker) {
        Query q = this.em.createNamedQuery("CarPosition.getPositionsOfRide");
        q.setParameter("rideId", rideId);
        q.setParameter("cartrackerId", cartracker);
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
     * Get highest serialNumber of rideId.
     * @param rideId of ride.
     * @param cartracker id of cartracker.
     * @return a serialNumber, else null.
     */
    public Long getLastSerialNumberOfRide(Integer rideId, String cartracker) {
        Query q = this.em
                .createNamedQuery("CarPosition.getLastSerialNumberOfRide");
        q.setParameter("rideId", rideId);
        q.setParameter("cartrackerId", cartracker);
        Long serialNumber = null;
        try {
            serialNumber = (Long) q.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            // Do nothing. Null will be returned.
            LOGGER.log(
                    Level.FINE,
                    "CarPosition: '" + rideId
                    + "' does not yet exist. " + ex);
        }
        return serialNumber;
    }
    
    /**
     * Get lowest serialNumber of rideId.
     * @param rideId of ride.
     * @param cartracker id of cartracker.
     * @return a serialNumber, else null.
     */
    public Long getFirstSerialNumberOfRide(Integer rideId, String cartracker) {
        Query q = this.em
                .createNamedQuery("CarPosition.getFirstSerialNumberOfRide");
        q.setParameter("rideId", rideId);
        q.setParameter("cartrackerId", cartracker);
        Long serialNumber = null;
        try {
            serialNumber = (Long) q.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            // Do nothing. Null will be returned.
            LOGGER.log(
                    Level.FINE,
                    "CarPosition: '" + rideId
                    + "' does not yet exist. " + ex);
        }
        return serialNumber;
    }
    

    /**
     * Get the coordinates in the given month and year for the given
     * cartrackerId.
     *
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

    public CarPosition findBySerialnumber(Long serialNumber) {
        Query q = this.em
                .createNamedQuery("CarPosition.getPositionsWithSerialnumber");
        q.setParameter("serialnumber", serialNumber);

        CarPosition cp = null;
        try {
            cp = (CarPosition) q.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            // Do nothing. Null will be returned.
            LOGGER.log(
                    Level.FINE,
                    "CarPosition: '" + serialNumber
                    + "' does not yet exist. " + ex);
        }
        return cp;
    }
}
