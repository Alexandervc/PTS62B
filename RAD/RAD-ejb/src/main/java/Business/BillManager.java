package business;

import dao.BillDAO;
import dao.RateDAO;
import domain.Bill;
import domain.Person;
import domain.Rate;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import service.IRoadUsage;

/**
 *
 * @author Melanie
 */
@Stateless
public class BillManager {
    @Inject
    private BillDAO billDAO;
    
    @Inject
    private RateDAO rateDAO;
    
    public void findBills(Person person) {
        billDAO.findAllForUser(person);
    }
    
    public void createBill(Bill bill) {
        billDAO.create(bill);
    }
    
    public Bill generateBill(Person person, List<IRoadUsage> roadUsages) {
        double totalPrice = 0;
        
        for (IRoadUsage ru  : roadUsages) {
            Rate rate = rateDAO.find(ru.getRoadType());
            double price = ru.getKm() * rate.getRate();
            totalPrice += price;
        }        
        
        Bill bill = new Bill(person, roadUsages, totalPrice);        
        return bill;
    }
}
