package dao;

import domain.Rate;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Implemented RateDao.
 * @author Melanie.
 */
@Stateless
public class RateDaoImp extends AbstractFacade<Rate> implements RateDao, Serializable {
    @PersistenceContext(unitName ="RADpu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }    
    
    public RateDaoImp() {
        super(Rate.class);
    }
}