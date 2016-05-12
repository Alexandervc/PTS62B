package business;

import dao.PersonDao;
import domain.Person;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Manager for PersonDao.
 * @author Melanie.
 */
@Stateless
public class PersonManager {
    @Inject
    private PersonDao personDAO;
    
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
    public Person createPerson(String firstname, String lastname, 
            String initials,String streetname, String number, String zipcode, 
            String city, String country) {
        
        Person person = new Person(firstname, lastname, initials,
            streetname, number, zipcode, city, country);
        this.personDAO.create(person);
        
        return person;
    }
    
    public Person findPersonByName(String name){
        return this.personDAO.findByName(name);
    }
    
    public Person findPersonById(Long personId) {
        return this.personDAO.find(personId);
    }
    
    /**
     * Search the persons with the given searchText in the 
     *      first name or last name.
     * @param searchText The text to search for. Cannot be null or empty.
     * @return List of found persons.
     */
    public List<Person> searchPersonsWithText(String searchText) {
        if(searchText == null || searchText.isEmpty()) {
            throw new IllegalArgumentException("searchText null or empty");
        }
        return this.personDAO.findPersonsWithText(searchText);
    }
}
