package dao;

import domain.Person;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Linda
 */
@Stateless
public class PersonDAOJPAImp extends AbstractFacade<Person> implements PersonDAO, Serializable {
    @PersistenceContext(unitName ="RADpu")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonDAOJPAImp() {
        super(Person.class);
    }
}
