package business;

import dao.PersonDAO;
import domain.Person;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Melanie
 */
@Stateless
public class PersonManager {
    @Inject
    private PersonDAO personDAO;
    
    public Person createPerson(String name) {
        Person person = new Person(name);
        personDAO.create(person);
        return person;
    }
}
