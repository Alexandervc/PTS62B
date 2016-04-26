package business;

import dao.BillDao;
import dao.RateDao;
import domain.Bill;
import domain.Person;
import domain.Rate;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import service.RoadUsage;

/**
 *  Manager for BillDao.
 * @author Melanie.
 */
@Stateless
public class BillManager {
    @Inject
    private BillDao billDAO;
    
    @Inject
    private RateDao rateDAO;
    
    /**
     * Find all bills in Database from person.
     * @param person Type Person.
     */
    public void findBills(Person person) {
        this.billDAO.findAllForUser(person);
    }
    
    /**
     * Create bill in Database.
     * @param bill Type Bill.
     */
    public void createBill(Bill bill) {
        this.billDAO.create(bill);
    }
    
    /**
     * Generate bill.
     * @param person Type Person.
     * @param roadUsages List of IRoadUsage.
     * @param cartrackerId.
     * @param month.
     * @param year.
     * @return new Bill Type Bill.
     */
    public Bill generateBill(Person person, List<RoadUsage> roadUsages, 
            Long cartrackerId, String month, String year) {
        double totalPrice = 0;
        
        for (RoadUsage ru  : roadUsages) {
            Rate rate = this.rateDAO.find(ru.getRoadType());
            double price = ru.getKm() * rate.getRate();
            totalPrice += price;
        }        
        
        Bill bill = new Bill(person, roadUsages, totalPrice, cartrackerId, 
                month, year);        
        return bill;
    }
}
