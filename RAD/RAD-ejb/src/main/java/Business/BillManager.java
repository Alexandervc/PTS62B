package business;

import dao.BillDAO;
import dao.RateDAO;
import domain.Bill;
import domain.Person;
import domain.Rate;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import service.RoadUsage;

/**
 *
 * @author Melanie.
 */
@Stateless
public class BillManager {
    @Inject
    private BillDAO billDAO;
    
    @Inject
    private RateDAO rateDAO;
    
    /**
     * Find all bills in Database from person.
     * @param person Type Person.
     */
    public void findBills(Person person) {
        billDAO.findAllForUser(person);
    }
    
    /**
     * Create bill in Database.
     * @param bill Type Bill.
     */
    public void createBill(Bill bill) {
        billDAO.create(bill);
    }
    
    /**
     * Generate bill.
     * @param person Type Person.
     * @param roadUsages List of IRoadUsage.
     * @return new Bill Type Bill.
     */
    public Bill generateBill(Person person, List<RoadUsage> roadUsages) {
        double totalPrice = 0;
        
        for (RoadUsage ru  : roadUsages) {
            Rate rate = rateDAO.find(ru.getRoadType());
            double price = ru.getKm() * rate.getRate();
            totalPrice += price;
        }        
        
        Bill bill = new Bill(person, roadUsages, totalPrice);        
        return bill;
    }
}
