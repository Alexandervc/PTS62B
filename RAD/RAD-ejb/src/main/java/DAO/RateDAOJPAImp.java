package dao;

import domain.Rate;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Melanie
 */
@Stateless
public class RateDAOJPAImp extends AbstractFacade<Rate> implements RateDAO, Serializable {
    //@PersistenceContext(unitName ="RADpu")
    @PersistenceContext(unitName = "DEVdbRADpu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }    
    
    public RateDAOJPAImp() {
        super(Rate.class);
    }
}