/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.CarPosition;
import domain.PreprocessCarposition;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Linda
 */
@Stateless
public class PreprocessCarpositionDao extends
        AbstractDaoFacade<PreprocessCarposition> {
    
    private static final Logger LOGGER
            = Logger.getLogger(PreprocessCarpositionDao.class.getName());
    
    @PersistenceContext
    private EntityManager em;
    
    /**
     * The dao for cartracker.
     */
    public PreprocessCarpositionDao() {
        super(PreprocessCarposition.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
    
     /**
     * Get the carpostions for the given rideId.
     *
     * @param rideId The id of the ride to get the carpositions for.
     * @param cartracker id of cartracker.
     * @return List of carpostions.
     */
    public List<PreprocessCarposition> getPositionsOfRide(Integer rideId, 
            String cartracker) {
        Query q = this.em.createNamedQuery("PreprocessCarposition."
                + "getPositionsOfRide");
        q.setParameter("rideId", rideId);
        q.setParameter("cartrackerId", cartracker);
        return q.getResultList();
    }

    public PreprocessCarposition findBySerialnumber(Long serialNumber, 
            String cartracker) {
        Query q = this.em
                .createNamedQuery("PreprocessCarposition."
                        + "getPositionsWithSerialnumber");
        q.setParameter("serialnumber", serialNumber);
        q.setParameter("cartrackerId", cartracker);

        PreprocessCarposition cp = null;
        try {
            cp = (PreprocessCarposition) q.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            // Do nothing. Null will be returned.
            LOGGER.log(
                    Level.FINE,
                    "PreprocessCarposition: '" + serialNumber
                    + "' does not yet exist. " + ex);
        }
        return cp;
    }
}
