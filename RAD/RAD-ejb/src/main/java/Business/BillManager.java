package business;

import dao.BillDAO;
import domain.Person;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Melanie
 */
@Stateless
public class BillManager {
    @Inject
    private BillDAO billDAO;
    
    public void getBills(Person person) {
        billDAO.findAllForUser(person);
    }
    
    public void generateBill() {
        
    }
}
