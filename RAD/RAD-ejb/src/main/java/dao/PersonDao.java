package dao;

import domain.Person;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Implemented PersonDao.
 * @author Linda.
 */
@Stateless
public class PersonDao extends AbstractFacade<Person> 
        implements Serializable {
    private static final int MAX_RESULTS = 20;
    
    private static final Logger LOGGER = Logger.
            getLogger(PersonDao.class.getName());

    @PersistenceContext
    private EntityManager em;

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
                    createNamedQuery("Person.findByName", Person.class);
            query.setParameter("name", name);
            person = query.getSingleResult();
            return person;
        } catch (NoResultException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Find person by cartrackerId.
     * @param cartrackerId The id of the cartracker.
     * @return The person object if found, otherwise null.
     */
    public Person findByCartrackerId(String cartrackerId) {
        Person person = null;
        
        try {
            TypedQuery<Person> query = this.em.createNamedQuery(
                    "Person.findByCartrackerId",
                    Person.class);
            query.setParameter("cartrackerId", cartrackerId);
            person = query.getSingleResult();
            return person;
        } catch (NoResultException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        
        return person;
    }
    
    /**
     * Find the persons with the given searchText in the 
     *      first name or last name.
     * @param searchText The text to search for.
     * @return List of found persons.
     */
    public List<Person> findPersonsWithText(String searchText) {
        Query q = this.em.createNamedQuery("Person.findPersonsWithText");
        q.setParameter("searchText", "%" + searchText + "%");
        q.setMaxResults(MAX_RESULTS);
        return (List<Person>) q.getResultList();
    }
}
