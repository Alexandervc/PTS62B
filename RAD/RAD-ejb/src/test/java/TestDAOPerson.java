/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import business.PersonManager;
import domain.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import service.RadService;

/**
 *
 * @author Linda
 */
@RunWith(MockitoJUnitRunner.class)
public class TestDAOPerson {
    RadService service;
    
    @Mock
    PersonManager personManager;
    
    Person person1;
    
    public TestDAOPerson() {
    }
    
    @Before
    public void setUp() {
        service = new RadService();
        service.setPersonManager(personManager);
        
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testAddPerson(){
        String firstname= "Linda";
        String lastname ="van Engelen";
        String initials = "LMJC";
  
        String streetname = "Sibeliuslaan";
        String number = "83";
        String zipcode = "5654CV";
        String city = "Eindhoven";
        String country = "Nederland";
        service.addPerson(firstname, lastname, initials, streetname, number, zipcode, city, country);
        verify(personManager, Mockito.times(1)).createPerson(firstname, lastname, initials, streetname, number, zipcode, city, country);
    }
}
