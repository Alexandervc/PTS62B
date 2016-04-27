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
 * Implemented PersonDao.
 * @author Linda.
 */
@Stateless
public class PersonDao extends AbstractFacade<Person> 
        implements Serializable {

    @PersistenceContext(unitName = "RADpu")
    private EntityManager em;
    
    private static final Logger LOGGER = Logger.
            getLogger(PersonDao.class.getName());

    /**
     * Contructor.
     */
    public PersonDao() {
        super(Person.class);
    }
    
    /**
     * Getter EntityManager.
     * @return EntityManager.
     */
    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    /**
     * Find person by name.
     * @param name String.
     * @return found person type Person.
     */
    public Person findByName(String name) {
        Person person;
        try {
            TypedQuery<Person> query = this.em.
                    createNamedQuery("person.findByName", Person.class);
            query.setParameter("name", name);
            person = query.getSingleResult();
            return person;
        } catch (NoResultException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
