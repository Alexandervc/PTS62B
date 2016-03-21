package DAO;

import Domain.Bill;
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
}
