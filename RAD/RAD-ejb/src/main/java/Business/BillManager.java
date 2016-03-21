package business;

import dao.BillDAO;
import domain.Person;
import javax.inject.Inject;

/**
 *
 * @author Melanie
 */
public class BillManager {
    @Inject
    private BillDAO billDAO;
    
    public void getBills(Person person) {
        billDAO.findAllForUser(person);
    }
    
    public void generateBill() {
        
    }
}
