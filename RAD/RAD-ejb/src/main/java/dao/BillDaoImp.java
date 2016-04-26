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
public class BillDaoImp extends AbstractFacade<Bill> 
        implements BillDao, Serializable {

    @PersistenceContext(unitName = "RADpu")
    private EntityManager em;

    /**
     * Contructor.
     */
    public BillDaoImp() {
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
    @Override
    public List<Bill> findAllForUser(Person person) {
        List<Bill> messages;
        TypedQuery<Bill> query = em.createNamedQuery("bill.findAllForUser", 
                Bill.class);
        query.setParameter("person", person);
        messages = query.getResultList();
        return messages;
    }
    
}
