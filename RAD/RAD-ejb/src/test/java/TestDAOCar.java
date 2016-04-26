/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import business.CarManager;
import domain.Car;
import domain.FuelType;
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
public class TestDAOCar {
    RadService service;
    
    @Mock
    CarManager carManager;
    
    Person person1;
    Car car1;
    
    public TestDAOCar() {
    }
    
    @Before
    public void setUp() {
        service = new RadService();
        service.setCarManager(carManager);
        
        person1 = new Person("Linda", "van Engelen", "LMJC", "Sibeliuslaan", "83",
                "5654CV", "Eindhoven", "Nederland");
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testAddCar(){
        service.addCar(person1, "PT123456789", FuelType.Petrol);
        verify(carManager, Mockito.times(1)).createCar(person1, "PT123456789", FuelType.Petrol);
    }
}
