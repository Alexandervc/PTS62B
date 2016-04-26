package business;

import domain.Bill;
import domain.Person;
import domain.Rate;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import service.RoadUsage;
import dao.BillDao;
import dao.RateDao;

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
     * @param cartrackerId.
     * @param month.
     * @param year.
     * @return new Bill Type Bill.
     */
    public Bill generateBill(Person person, List<RoadUsage> roadUsages, 
            String cartrackerId, String month, String year) {
        double totalPrice = 0;
        
        for (RoadUsage ru  : roadUsages) {
            Rate rate = rateDAO.find(ru.getRoadType());
            //rate= new Rate(1, RoadType.A);
            double price = ru.getKm() * rate.getRate();
            totalPrice += price;
        }        
        
        Bill bill = new Bill(person, roadUsages, totalPrice, cartrackerId, month, year);        
        return bill;
    }
}
