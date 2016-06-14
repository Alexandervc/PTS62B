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
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Service for person.
 * @author Alexander
 */
@Stateless
public class PersonService {
    
    @Inject
    private PersonManager personManager;
    
    /**
     * Add person to database.
     * @param firstname String.
     * @param lastname String.
     * @param initials String.
     * @param username String.
     * @param password String.
     * @param address Address.
     * @return created person type Person.
     */
    public Person addPerson(String firstname, String lastname, String initials,
            String username, String password,Address address) {
        return this.personManager.createPerson(firstname, lastname,
                initials, username, password, address);
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
    
    /**
     * Find person by cartrackerId.
     * @param cartrackerId The id of the cartracker.
     * @return The person object if found, otherwise null.
     */
    public Person findPersonByCartrackerId(String cartrackerId) {
        return this.personManager.findPersonByCartrackerId(cartrackerId);
    }
    
    /**
     * Find person by username.
     * @param username of person.
     * @return Found person, otherwise null.
     */
    public Person findPersonByUsername(String username){
        return this.personManager.findPersonByUsername(username);
    }
}
