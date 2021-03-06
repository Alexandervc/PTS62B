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
public class RateDao extends AbstractFacade<Rate> implements Serializable {
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Constructor.
     */
    public RateDao() {
        super(Rate.class);
    }

    /**
     * Getter EntityManager.
     * @return em type EntityManager.
     */
    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
}