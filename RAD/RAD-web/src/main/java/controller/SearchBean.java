/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domain.Person;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import service.PersonService;

/**
 *
 * @author Edwin
 */
@Named
@RequestScoped
public class SearchBean {
    
    private static final Logger LOGGER = Logger
            .getLogger(SearchBean.class.getName());
    
    @EJB
    private PersonService service;
    
    private List<Person> persons;
    
    private String searchString;

    public List<Person> getPersons() {
        return this.persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
    
    public String getSearchString() {
        return this.searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
    
    public void Search() {
        if(this.searchString.isEmpty()) return;
        this.persons = service.searchPersons(this.searchString);
    }
}
