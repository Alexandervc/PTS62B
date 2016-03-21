package business;

import dao.PersonDAO;
import domain.Person;
import javax.inject.Inject;

/**
 *
 * @author Melanie
 */
public class PersonManager {
    @Inject
    private PersonDAO personDAO;
    
    public Person addPerson(String name) {
        Person person = new Person(name);
        personDAO.create(person);
        return person;
    }
}
