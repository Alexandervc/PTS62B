package business;

import domain.Rate;
import domain.RoadType;
import javax.ejb.Stateless;
import javax.inject.Inject;
import dao.RateDao;

/**
 * Manager for RateDao.
 * @author Melanie.
 */
@Stateless
public class RateManager {
    @Inject
    private RateDao rateDAO;
    
    /**
     * Create Rate in Database.
     * @param rate price in double.
     * @param type RoadType.
     */
    public void createRate(double rate, RoadType type) {
        Rate r = new Rate(rate, type);
        rateDAO.create(r);
    }
   
    /**
     * Get Rate from Database.
     * @param type RoadType.
     * @return found Rate type Rate.
     */
    public Rate findRate(RoadType type) {
        return rateDAO.find(type);
    }
}
