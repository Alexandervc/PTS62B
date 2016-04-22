package dao;

import domain.Bill;
import domain.Person;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Implemented BillDAO
 * @author Linda
 */
@Stateless
public class BillDAOJPAImp extends AbstractFacade<Bill> implements BillDAO, Serializable {

    @PersistenceContext(unitName = "RADpu")
    private EntityManager em;

    private Logger logger;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BillDAOJPAImp() {
        super(Bill.class);
        logger = Logger.getLogger(BillDAOJPAImp.class.getName());
    }

    @Override
    public List<Bill> findAllForUser(Person person) {
        List<Bill> messages;
        TypedQuery<Bill> query = em.createNamedQuery("bill.findAllForUser", Bill.class);
        // TODO user ipv person??
        query.setParameter("user", person);
        messages = query.getResultList();
        return messages;
    }
}
