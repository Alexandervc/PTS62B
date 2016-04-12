package business1;

import dao1.RateDAO;
import domain1.Rate;
import domain1.RoadType;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Melanie.
 */
@Stateless
public class RateManager {
    @Inject
    private RateDAO rateDAO;
    
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
