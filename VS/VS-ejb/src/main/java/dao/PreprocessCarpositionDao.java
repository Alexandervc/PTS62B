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
 * Dao class for preprocesDarposition.
 *
 * @author Linda.
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

    /**
     * Find carposition with serialnumber and cartrackerid.
     *
     * @param serialNumber Long of carposition number.
     * @param cartracker String of cartrackerid.
     * @return PreprocessCarposition, when not found return null;
     */
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

    /**
     * Search for missing serialnumbers in database.
     *
     * @return Map of cartrackers with missing serialnumbers.
     */
    public Map<String, List<Long>> searchForMissingNumbers() {
        String missingNumbers = "";
        Map<String, List<Long>> map = new HashMap<>();
        // call procedure in database.
        StoredProcedureQuery query = this.em.
                createStoredProcedureQuery("searchMissingNumber");
        query.registerStoredProcedureParameter("numbers", String.class,
                ParameterMode.OUT);
        query.setParameter("numbers", missingNumbers);

        query.execute();
        // Get return string.
        missingNumbers = (String) query.getOutputParameterValue("numbers");
        // convert string to map.
        if (!missingNumbers.isEmpty()) {
            map = this.convertStringToMap(missingNumbers);
        }
        LOGGER.log(Level.INFO, "searchForMissingNumbers: " + map);
        return map;
    }

    /**
     * Get all cartrackers from preprocessDatabase.
     * @return List of cartrackerid's.
     */
    public List<String> getAllCartrackers() {
        Query q = this.em.createNamedQuery("PreprocessCarposition."
                + "getCartrackerIds");
        List<String> cartrackers = (List<String>) q.getResultList();
        return cartrackers;
    }

    /**
     * Get all ride-id's from cartracker inside PreprocessDatabase.
     * @param cartracker String of cartrackerid.
     * @return Lint of integers with ride-id's
     */
    public List<Integer> getAllRideIdFromCartracker(String cartracker) {
        Query q = this.em.createNamedQuery("PreprocessCarposition."
                + "getRideids");
        q.setParameter("cartrackerId", cartracker);
        List<Integer> cartrackers = (List<Integer>) q.getResultList();
        return cartrackers;
    }

    private Map<String, List<Long>> convertStringToMap(String message) {
        Map<String, List<Long>> map = new HashMap<>();
        // Split for cartrackers
        String[] array = message.split(",");
        for (String s : array) {
            if (!s.isEmpty()) {
                // Split for serialnumbers
                String[] object = s.split(":");
                String cartracker = object[0];
                String number = object[1];
                // Add serialnumber to existing cartracker.
                map = this.addObjectToMap(map, cartracker, number);
            }
        }
        return map;
    }

    private Map<String, List<Long>> addObjectToMap(Map<String, List<Long>> map,
            String cartracker, String serialnumber) {
        Map<String, List<Long>> temp = map;
        if (temp.containsKey(cartracker)) {
            List<Long> newObject = temp.get(cartracker);
            newObject.add(Long.parseLong(serialnumber));
            temp.replace(cartracker, newObject);
        } else {
            // Else create new key.
            List<Long> newObject = new ArrayList<>();
            newObject.add(Long.parseLong(serialnumber));
            temp.put(cartracker, newObject);
        }
        return temp;
    }
}
