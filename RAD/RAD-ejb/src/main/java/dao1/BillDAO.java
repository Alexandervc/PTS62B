package dao1;

import domain1.Bill;
import domain1.Person;
import java.util.List;

/**
 *
 * @author Linda
 */
public interface BillDAO {
    void create(Bill bill);
    void edit(Bill bill);
    void remove(Bill bill);
    Bill find(Object id);    
    List<Bill> findAll();    
    int count();
    
    public List<Bill> findAllForUser(Person person);
}