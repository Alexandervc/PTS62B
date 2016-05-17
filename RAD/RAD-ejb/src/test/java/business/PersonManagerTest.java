/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dao.PersonDao;
import domain.Address;
import domain.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.verify;

/**
 *
 * @author Linda
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonManagerTest {
    
    private PersonManager manager;
    
    @Mock
    private PersonDao dao;
    
    public PersonManagerTest() {
        // Empty for JPA.
    }
    
    @Before
    public void setUp() {
        this.manager = new PersonManager();
        this.manager.setPersonDAO(this.dao);
        
    }
    
    @Test
    public void addPerson(){
        String firstname= "Linda";
        String lastname ="van Engelen";
        String initials = "LMJC";
        
        String streetname = "Sibeliuslaan";
        String number = "83";
        String zipcode = "5654CV";
        String city = "Eindhoven";
        
        Address adres = new Address(streetname, number, zipcode, city);
        Person person = this.manager.createPerson(firstname, lastname, initials, adres);
        
        verify(this.dao, Mockito.times(1)).create(person);
    }

    @Test
    public void searchPerson(){
        String searchText = "L e";
        
        this.manager.searchPersonsWithText(searchText);
        
        verify(this.dao, Mockito.times(1)).findPersonsWithText(searchText);
    }
    
    @Test
    public void findByName(){
        String name = "Fernando";
        
        this.manager.findPersonByName(name);
        
        verify(this.dao, Mockito.times(1)).findByName(name);
    }
    
    @Test
    public void findById(){
        Long id = 1L;
        
        this.manager.findPersonById(id);
        
        verify(this.dao, Mockito.times(1)).find(id);
    }
}
