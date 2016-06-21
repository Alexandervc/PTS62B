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
import domain.RoadType;
import dto.RoadUsage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The dao for road usages.
 * @author Edwin
 */
@Stateless
public class RoadUsageDao extends AbstractDaoFacade<CarPosition> {
    
    private static final Logger LOGGER
            = Logger.getLogger(RoadUsageDao.class.getName());
    
    @PersistenceContext
    private EntityManager em;

    /**
     * The dao for road usage.
     */
    public RoadUsageDao() {
        super(CarPosition.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
    
    /**
     * Gets RoadUsages from the materializedView based on a cartrackerid, a
 month and a year.
     * @param cartrackerid The id of the cartracker with the roadusages that 
     *      are requested.
     * @param month The month that is being requested.
     * @param year The year the month is in.
     * @return A list of RoadUsages from the person.
     */
    public List<RoadUsage> getRoadUsages(String cartrackerid,
            String month, String year ) {
        Query query = this.em.createNativeQuery(
                "select * from MV_roadusage"
                + " WHERE cartracker_id = ? AND month = ? AND year = ?");
        query.setParameter(1, cartrackerid);
        query.setParameter(2, month);
        query.setParameter(3, year);

        List<RoadUsage> returnList = new ArrayList<>();
        List<Object[]> roadUsages = query.getResultList(); 
        for(Object[] o : roadUsages) {
            String roadName = o[3].toString();
            Double km = Double.valueOf(o[5].toString());
            RoadType type = RoadType.valueOf(o[4].toString());
            Long foreignCountryRoadId;
            try {
                foreignCountryRoadId = Long.valueOf(o[6].toString());
            } catch (NumberFormatException ex) {
                foreignCountryRoadId = null;
                LOGGER.log(Level.INFO,null,ex);
            }
            RoadUsage usage = new RoadUsage(roadName
                    ,type
                    ,km
                    ,foreignCountryRoadId);
            returnList.add(usage);
        }
        return returnList;
    }

}
