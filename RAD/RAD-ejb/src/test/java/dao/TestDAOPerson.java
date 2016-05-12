/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import business.PersonManager;
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
 * Test PersonDao.
 *
 * @author Linda.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestDAOPerson {

    private RadService service;

    @Mock
    private PersonManager personManager;

    /**
     * Constructor.
     */
    public TestDAOPerson() {
        // Empty for start up test
    }

    /**
     * Set up at beginning test.
     */
    @Before
    public void setUp() {
        this.service = new RadService();
        this.service.setPersonManager(this.personManager);

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddPerson() {
        String firstname = "Linda";
        String lastname = "van Engelen";
        String initials = "LMJC";

        String streetname = "Sibeliuslaan";
        String number = "83";
        String zipcode = "5654CV";
        String city = "Eindhoven";
        String country = "Nederland";
        this.service.addPerson(firstname, lastname, initials, streetname,
                number, zipcode, city, country);
        verify(this.personManager, Mockito.times(1)).createPerson(firstname,
                lastname, initials, streetname, number, zipcode, city,
                country);
    }
}
