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
import java.io.IOException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import util.ClobUtil;
import util.DateUtil;

/**
 * The dao for carPosition.
 * @author Alexander
 */
@Stateless
public class CarPositionDao extends AbstractDaoFacade<CarPosition> {
    private static final Logger LOGGER = Logger
            .getLogger(CarPositionDao.class.getName());
    
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
     * @return A JSON-string of coordinates.
     */
    public String getCoordinates(int month, int year,
            String cartrackerId) {
        try {
            // Get begin date and end date
            Date beginDate = DateUtil.getFirstOfMonth(month, year);   
            Date endDate = DateUtil.getLastOfMonth(month, year);
            
            // Create stored procedure
            StoredProcedureQuery spq = em
                    .createStoredProcedureQuery("get_coordinates");
            
            // Register parameters
            spq.registerStoredProcedureParameter("v_return", 
                    Clob.class, ParameterMode.OUT);
            spq.registerStoredProcedureParameter("p_cartracker_id", 
                    String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("p_begin_date", 
                    Date.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("p_end_date", 
                    Date.class, ParameterMode.IN);
            
            // Create CLOB
            Connection conn = em.unwrap(Connection.class);
            Clob clob = conn.createClob();
            
            // Set parameters
            spq.setParameter("v_return", clob);
            spq.setParameter("p_cartracker_id", cartrackerId);
            spq.setParameter("p_begin_date", beginDate);
            spq.setParameter("p_end_date", endDate);
            
            // Execute stored procedure
            spq.execute();
            
            // Get output
            Clob coordinatesClob = 
                    (Clob) spq.getOutputParameterValue("v_return");
            
            // Convert and return output
            return ClobUtil.convertClobToString(coordinatesClob);
        } catch (SQLException | IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
