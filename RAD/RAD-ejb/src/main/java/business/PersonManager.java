package business;

import dao.PersonDao;
import dao.UserGroupDao;
import domain.Address;
import domain.Person;
import domain.UserGroup;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import util.Hasher;

/**
 * Manager for PersonDao.
 *
 * @author Melanie.
 */
@Stateless
public class PersonManager {
    private static final String userGroupName = "user";

    @Inject
    private PersonDao personDAO;
    
    @Inject
    private UserGroupDao userGroupDao;

    /**
     * Create person in Database.
     *
     * @param firstname String.
     * @param lastname String.
     * @param initials String.
     * @param address Address.
     * @return new person Type Person.
     */
    public Person createPerson(String firstname, String lastname,
            String initials, String username, String password,
            Address address) {
        String hashedPassword = Hasher.hash(password);
        
        Person person = new Person(firstname, lastname, initials, username,
                hashedPassword, address);
        
        // Add security group
        UserGroup userGroup = (UserGroup) this.userGroupDao.find(userGroupName);
        if(userGroup == null) {
            userGroup = new UserGroup(userGroupName);
        }
        person.addGroup(userGroup);
        
        // Create person
        this.personDAO.create(person);

        return person;
    }

    /**
     * Find person by name.
     *
     * @param name of person.
     * @return object person.
     */
    public Person findPersonByName(String name) {
        return this.personDAO.findByName(name);
    }

    /**
     * Find person by personId.
     *
     * @param personId of person.
     * @return object person.
     */
    public Person findPersonById(Long personId) {
        return this.personDAO.find(personId);
    }

    /**
     * Find person by cartrackerId.
     *
     * @param cartrackerId The id of the cartracker.
     * @return The person object if found, otherwise null.
     */
    public Person findPersonByCartrackerId(String cartrackerId) {
        return this.personDAO.findByCartrackerId(cartrackerId);
    }

    /**
     * Search the persons with the given searchText in the first name or last
     * name.
     *
     * @param searchText The text to search for. Cannot be null or empty.
     * @return List of found persons.
     */
    public List<Person> searchPersonsWithText(String searchText) {
        String trimSearchText = null;
        if (searchText != null) {
            trimSearchText = searchText.trim();
        }
        if (trimSearchText == null || trimSearchText.isEmpty()) {
            throw new IllegalArgumentException("searchText null or empty");
        }
        return this.personDAO.findPersonsWithText(trimSearchText);
    }

    /**
     * Find person by username.
     * @param username of person.
     * @return Found person, otherwise null.
     */
    public Person findPersonByUsername(String username) {
        return this.personDAO.findPersonByUsername(username);
    }

    /**
     * Setter PersonDAO.
     *
     * @param personDAO object.
     */
    public void setPersonDAO(PersonDao personDAO) {
        this.personDAO = personDAO;
    }

}
