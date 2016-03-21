package business;

import dao.RateDAO;
import domain.Rate;
import domain.RoadType;
import javax.inject.Inject;

/**
 *
 * @author Melanie
 */
public class RateManager {
    @Inject
    private RateDAO rateDAO;
    
    public void addRate(double rate, RoadType type) {
        Rate r = new Rate(rate, type);
        rateDAO.create(r);
    }
}
