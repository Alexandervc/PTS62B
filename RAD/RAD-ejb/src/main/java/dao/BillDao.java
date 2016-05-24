package dao;

import domain.Bill;
import domain.Person;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Implemented BillDao.
 * @author Linda.
 */
@Stateless
public class BillDao extends AbstractFacade<Bill> 
        implements Serializable {

    @PersistenceContext
    private EntityManager em;

    /**
     * Contructor.
     */
    public BillDao() {
        super(Bill.class);
    }
    
    /**
     * Getter EntityManager.
     * @return EntityManager
     */
    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    /**
     * Find all bills from person.
     * @param person type Person.
     * @return List bills.
     */
    public List<Bill> findAllForUser(Person person) {
        List<Bill> messages;
        TypedQuery<Bill> query = this.em
                .createNamedQuery("Bill.findAllForUser", Bill.class);
        query.setParameter("person", person);
        messages = query.getResultList();
        return messages;
    }
    
    /**
     * Find Bill by cartrackerid, month and year.
     * @param cartrackerId Long.
     * @param month integer.
     * @param year integer.
     * @return object Bill.
     */
    public Bill findBillWithCartracker(String cartrackerId, int month, int year){
        Bill b;
        TypedQuery<Bill> query = this.em
                .createNamedQuery("Bill.findBillWithCartracker", Bill.class);
        query.setParameter("cartracker", cartrackerId);
        query.setParameter("month", month);
        query.setParameter("year", year);
        
        b = query.getSingleResult();
        return b;
    }
    
}
