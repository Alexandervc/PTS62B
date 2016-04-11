package dao;

import domain.Person;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Linda
 */
@Stateless
public class PersonDAOJPAImp extends AbstractFacade<Person> implements PersonDAO, Serializable {

    @PersistenceContext(unitName = "RADpu")
    //@PersistenceContext(unitName = "DEVdbRADpu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonDAOJPAImp() {
        super(Person.class);
    }

    @Override
    public Person findByName(String name) {
        Person person;
        try {
            TypedQuery<Person> query = em.createNamedQuery("person.findByName", Person.class);
            query.setParameter("name", name);
            person = query.getSingleResult();
            return person;
        } catch (Exception e) {
            System.out.println("No person found: " + e.getMessage());
        }
        return null;
    }

}
