package business1;

import dao1.PersonDAO;
import domain1.Person;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Melanie.
 */
@Stateless
public class PersonManager {
    @Inject
    private PersonDAO personDAO;
    
    /**
     * Create person in Database.
     * @param firstname String.
     * @param lastname String.
     * @param initials String.
     * @param streetname String.
     * @param number String.
     * @param zipcode String.
     * @param city String.
     * @param country String.
     * @return new person Type Person.
     */
    public Person createPerson(String firstname, String lastname, String initials,
            String streetname, String number, String zipcode, 
            String city, String country) {
        
        Person person = new Person(firstname, lastname, initials,
            streetname, number, zipcode, city, country);
        personDAO.create(person);
        
        return person;
    }
    
    public Person findPersonByName(String name){
        Person person = personDAO.findByName(name);
        return person;
    }
}
