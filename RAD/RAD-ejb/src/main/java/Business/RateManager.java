package business;

import dao.RateDAO;
import domain.Rate;
import domain.RoadType;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Melanie
 */
@Stateless
public class RateManager {
    @Inject
    private RateDAO rateDAO;
    
    public void createRate(double rate, RoadType type) {
        Rate r = new Rate(rate, type);
        rateDAO.create(r);
    }
   
    public Rate findRate(RoadType type) {
        return rateDAO.find(type);
    }
}
