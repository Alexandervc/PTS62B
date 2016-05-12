/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import business.PersonManager;
import domain.Person;
import java.util.List;
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
     * Search the persons with the given searchText in the 
     *      first name or last name.
     * @param searchText The text to search for.
     * @return List of found persons.
     */
    public List<Person> searchPersons(String searchText) {
        return this.personManager.searchPersonsWithText(searchText);
    }
}
