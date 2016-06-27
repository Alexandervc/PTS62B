/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.PreprocessCarposition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

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

    public Map<String, List<Long>> searchForMissingNumbers() {
        String missingNumbers = "";
        Map<String, List<Long>> map = new HashMap<>();
        try {
            StoredProcedureQuery query = this.em.
                    createStoredProcedureQuery("searchMissingNumber");
            query.registerStoredProcedureParameter("numbers", String.class,
                    ParameterMode.OUT);
            query.setParameter("numbers", missingNumbers);

            query.execute();
            missingNumbers = (String) query.getOutputParameterValue("numbers");
            if (!missingNumbers.isEmpty()) {
                String[] array = missingNumbers.split(",");

                for (String s : array) {
                    if (!s.isEmpty()) {
                        String[] object = s.split(":");
                        String cartracker = object[0];
                        String number = object[1];
                        if (map.containsKey(cartracker)) {
                            List<Long> newObject = map.get(cartracker);
                            newObject.add(Long.parseLong(number));
                            map.replace(cartracker, newObject);
                        } else {
                            List<Long> newObject = new ArrayList<>();
                            newObject.add(Long.parseLong(number));
                            map.put(cartracker, newObject);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.INFO, e.getMessage());
        }
        LOGGER.log(Level.INFO, "searchForMissingNumbers: " + map);
        return map;
    }

    public List<String> getAllCartrackers() {
        List<String> cartrackers = new ArrayList<>();
        Query q = this.em.createNamedQuery("PreprocessCarposition."
                + "getCartrackerIds");
        cartrackers = (List<String>) q.getResultList();
        return cartrackers;
    }

    public List<Integer> getAllRideIdFromCartracker(String cartracker) {
        List<Integer> cartrackers = new ArrayList<>();
        Query q = this.em.createNamedQuery("PreprocessCarposition."
                + "getRideids");
        q.setParameter("cartrackerId", cartracker);
        cartrackers = (List<Integer>) q.getResultList();
        return cartrackers;
    }
}
