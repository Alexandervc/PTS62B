/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import business.PersonManager;
import domain.Address;
import domain.Person;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Service for person.
 * @author Alexander
 */
@Stateless
public class PersonService {
    private static final Logger LOGGER =
            Logger.getLogger(PersonService.class.getName());
    
    @Inject
    private PersonManager personManager;
    
    /**
     * Add person to database.
     *
     * @param firstname String.
     * @param lastname String.
     * @param initials String.
     * @param address Address.
     * @return created person type Person.
     */
    public Person addPerson(String firstname, String lastname, String initials,
            Address address) {
        return this.personManager.createPerson(firstname, lastname,
                initials, address);
    }
    
    /**
     * Search the persons with the given searchText in the 
     *      first name or last name.
     * @param searchText The text to search for.
     * @return List of found persons.
     */
    public List<Person> searchPersons(String searchText) {
        return this.personManager.searchPersonsWithText(searchText);
    }
    
    /**
     * Find person in database by personId.
     * 
     * @param personId id of person.
     * @return found person.
     */
    public Person findPersonById(Long personId) {
        return this.personManager.findPersonById(personId);
    }    
    
}
