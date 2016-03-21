package DAO;

import Domain.Bill;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
