package dao;

import domain.Person;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Implemented PersonDAO
 * @author Linda
 */
@Stateless
public class PersonDAOJPAImp extends AbstractFacade<Person> implements PersonDAO, Serializable {

    @PersistenceContext(unitName = "RADpu")
    private EntityManager em;
    
    private Logger logger;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonDAOJPAImp() {
        super(Person.class);
        logger = Logger.getLogger(PersonDAOJPAImp.class.getName());
    }

    @Override
    public Person findByName(String name) {
        Person person;
        try {
            TypedQuery<Person> query = em.createNamedQuery("person.findByName", Person.class);
            query.setParameter("name", name);
            person = query.getSingleResult();
            return person;
        } catch (NoResultException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
