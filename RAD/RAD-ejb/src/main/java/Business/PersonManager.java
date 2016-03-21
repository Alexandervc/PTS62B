package Business;

import DAO.PersonDAO;
import Domain.Person;
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
