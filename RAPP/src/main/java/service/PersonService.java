/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dto.PersonDto;
import javax.ejb.Stateless;
import javax.inject.Inject;
import service.rest.clients.PersonClient;

/**
 * Service for person.
 * @author Alexander
 */
@Stateless
public class PersonService {
    @Inject
    private PersonClient personClient;
    
    /**
     * Get the person with the given username
     *
     * @param username The username of the person.
     * @return The found person.
     */
    public PersonDto getPerson(String username) {
        return this.personClient.getPerson(username);
    }
}
