/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domain.Person;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import service.PersonService;

/**
 * Managed bean for the index page.
 * @author Edwin.
 */
@Named
@RequestScoped
public class SearchBean {
    
    @EJB
    private PersonService service;
    
    private List<Person> persons;
    
    private String searchString;

    public List<Person> getPersons() {
        return this.persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = new ArrayList<>(persons);
    }
    
    public String getSearchString() {
        return this.searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
    

    /**
     * Search for persons that have the searchString in their name.
     * Updates the lists of persons.
     */
    public void searchPersons() {
        this.searchString = this.searchString.trim();
        if(!this.searchString.isEmpty()) {
            this.persons = this.service.searchPersons(this.searchString);
        }
    }
}
