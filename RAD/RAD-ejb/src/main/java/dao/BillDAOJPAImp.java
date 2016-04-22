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
 *
 * @author Linda
 */
@Stateless
public class BillDAOJPAImp extends AbstractFacade<Bill> implements BillDAO, Serializable {

    @PersistenceContext(unitName = "RADpu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BillDAOJPAImp() {
        super(Bill.class);
    }

    @Override
    public List<Bill> findAllForUser(Person person) {
        List<Bill> messages;
        TypedQuery<Bill> query = em.createNamedQuery("bill.findAllForUser", Bill.class);
        query.setParameter("user", person);
        messages = query.getResultList();
        return messages;
    }
}
