package dao;

import domain.Bill;
import domain.Person;
import java.util.List;

/**
 * BillDao interface.
 * @author Linda.
 */
public interface BillDao {
    void create(Bill bill);
    void edit(Bill bill);
    void remove(Bill bill);
    Bill find(Object id);    
    List<Bill> findAll();    
    int count();
    
    public List<Bill> findAllForUser(Person person);
}