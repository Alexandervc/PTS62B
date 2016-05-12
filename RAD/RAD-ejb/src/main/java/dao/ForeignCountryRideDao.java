/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.ForeignCountryRide;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Provides functionality regarding the ForeignCountryRideDao.
 * @author Jesse
 */
@Stateless
public class ForeignCountryRideDao extends AbstractFacade<ForeignCountryRide> 
        implements Serializable {

    @PersistenceContext(unitName = "RADpu")
    private EntityManager em;
    
    /**
     * Gets the EntityManager.
     * @return EntityManager
     */
    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
    
    private static final Logger LOGGER = Logger
            .getLogger(ForeignCountryRideDao.class.getName()); 

    /**
     * Instantiates the ForeignCountryRideDao class.
     */
    public ForeignCountryRideDao() {
        super(ForeignCountryRide.class);
    }
    
    /**
     * Finds the ForeignCountryRide by ForeignCountryRideId.
     * @param foreignCountryRideId The id of the foreignCountryRide.
     * @return Returns a ForeignCountryRide object if the query returns exactly 
     *     one result, otherwise null.
     */
    public ForeignCountryRide findByForeignCountryRideId(
            String foreignCountryRideId) {
        
        ForeignCountryRide returnValue = null;
        
        // Call the named query "foreignCountryRide.findByForeignCountryRideId" 
        // with the foreignCountryRideId as parameter.
        TypedQuery<ForeignCountryRide> query = this.em
                .createNamedQuery(
                        "foreignCountryRide.findByForeignCountryRideId", 
                        ForeignCountryRide.class);
        query.setParameter("foreignCountryRideId", foreignCountryRideId);
        
        // Try to get the result of the query, this throws a NoResultException
        // if the query did not return exactly one result.
        try {
            returnValue = query.getSingleResult();
        } catch (NoResultException ex) {
            // If the query does not return any results, log the exception 
            // message with the foreignCountryRideId parameter.
            LOGGER.log(Level.INFO, null, ex);
        } catch (NonUniqueResultException ex) {
            // If the query returns more then one resut, log the exception 
            // message with the foreignCountryRideId parameter.
            LOGGER.log(Level.SEVERE, null, ex);
        }
        
        // Returns null if the query did not return exactly one result.
        return returnValue;
    }
}