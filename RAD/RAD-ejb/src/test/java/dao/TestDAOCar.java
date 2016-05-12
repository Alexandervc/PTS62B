
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import business.CarManager;
import domain.FuelType;
import domain.Person;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import service.RadService;

/**
 * Test Car Dao
 *
 * @author Linda
 */
@RunWith(MockitoJUnitRunner.class)
public class TestDAOCar {

    private RadService service;

    @Mock
    private CarManager carManager;

    private Person person1;

    private static final String ID = "PT123456789";
    private static final FuelType FUEL = FuelType.Diesel;

    /**
     * Constructor.
     */
    public TestDAOCar() {
        // Empty for start up test
    }

    /**
     * Set up at beginning test.
     */
    @Before
    public void setUp() {
        this.service = new RadService();
        this.service.setCarManager(this.carManager);

        this.person1 = new Person("Linda", "van Engelen", "LMJC", "Sibeliuslaan",
                "83", "5654CV", "Eindhoven", "Nederland");
    }

    /**
     * Test adding car.
     */
    @Test
    public void testAddCar() {
        this.service.addCar(this.person1, ID, FUEL);
        verify(carManager, Mockito.times(1)).createCar(person1, ID, FUEL);
    }
}
