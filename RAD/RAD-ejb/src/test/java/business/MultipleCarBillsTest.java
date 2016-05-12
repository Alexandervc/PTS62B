/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import domain.Bill;
import domain.Car;
import domain.FuelType;
import domain.Person;
import domain.RoadType;
import dto.RoadUsage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author Linda
 */
//@RunWith(MockitoJUnitRunner.class)
public class MultipleCarBillsTest {
    /*
    private RadService service;
        
    @Mock
    private RoadUsagesService roadUsagesService;
        
    @Mock
    private BillManager billManager;
    
    @Mock
    private PersonManager personManager;
    
    @Mock
    private CarManager carManager;
    
    @Mock
    private RateManager rateManager;
    
    private Person person1;
    private Person person2;
    
    private Date begin;
    private Date end;
    
    private String month;
    private String year;
    
    private List<RoadUsage> roadUsage;
    
    public MultipleCarBillsTest() {
    }
    
    @Before
    public void setUp() {
        this.service = new RadService();
        // set managers and service with Mock
        this.service.setRoadUsagesService(this.roadUsagesService);
        this.service.setPersonManager(this.personManager);
        this.service.setBillManager(this.billManager);
        this.service.setCarManager(this.carManager);
        this.service.setRateManager(this.rateManager);
        
        this.begin = new Date();
        this.end = new Date();
        // format date
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM",
                Locale.getDefault());
        // set month for bill
        this.month = dateFormat.format(begin);
        // set year for bill
        this.year = Integer.toString(begin.getYear() + 1900);
        
        this.createPerson();
        
        
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    
    public void getmultipleNewBillsTest(){        
        // Define when
        when(this.service.findPersonByName(this.person1.getFirstName()))
                .thenReturn(this.person1);
        
        when(this.roadUsagesService.getRoadUsages("PT123456789", begin, end))
                .thenReturn(this.roadUsage);
         when(this.roadUsagesService.getRoadUsages("PT987654321", begin, end))
                .thenReturn(this.roadUsage);
        
        this.service.generateBill(this.person1.getFirstName(), begin, end);
        verify(this.roadUsagesService, Mockito.times(1))
                .getRoadUsages("PT123456789", begin, end);
        verify(this.roadUsagesService, Mockito.times(1))
                .getRoadUsages("PT987654321", begin, end);
        
        verify(this.billManager, Mockito.times(1))
                .generateBill(this.person1, this.roadUsage, "PT123456789", 
                        this.month, this.year);
        verify(this.billManager, Mockito.times(1))
                .generateBill(this.person1, this.roadUsage, "PT987654321", 
                        this.month, this.year);
    }
    
    @Test
    public void getmultipleOldBillsTest(){
        // Define when
        when(this.service.findPersonByName(this.person2.getFirstName()))
                .thenReturn(this.person2);
        
         when(this.roadUsagesService.getRoadUsages("PT123456789", begin, end))
                .thenReturn(this.roadUsage);
         when(this.roadUsagesService.getRoadUsages("PT987654321", begin, end))
                .thenReturn(this.roadUsage);
         
        this.service.generateBill(this.person2.getFirstName(), begin, end);
        verify(this.roadUsagesService, Mockito.times(1))
                .getRoadUsages("PT123456789", begin, end);
        verify(this.roadUsagesService, Mockito.times(1))
                .getRoadUsages("PT987654321", begin, end);
        
        verify(this.billManager, Mockito.times(1))
                .generateBill(this.person2, this.roadUsage, 
                        "PT987654321", this.month, this.year);
        verify(this.billManager, Mockito.times(0))
                .generateBill(this.person2, this.roadUsage, 
                        "PT123456789", this.month, this.year);
    }
    
    private void createPerson(){
        String firstname= "Linda";
        String lastname ="van Engelen";
        String initials = "LMJC";
  
        String streetname = "Sibeliuslaan";
        String number = "83";
        String zipcode = "5654CV";
        String city = "Eindhoven";
        String country = "Nederland";
        this.person1 = new Person(firstname, lastname, initials, 
                streetname, number, zipcode, city, country);
        this.person2 = new Person(firstname, lastname, initials, 
                streetname, number, zipcode, city, country);
        
        String id1 ="PT123456789";
        String id2 ="PT987654321";
        Car c1 = new Car(this.person1, id1 , FuelType.Diesel);
        Car c2 = new Car(this.person1, id2, FuelType.Petrol);
        
        Car c3 = new Car(this.person2, id1 , FuelType.Diesel);
        Car c4 = new Car(this.person2, id2, FuelType.Petrol);
        
        Bill b = new Bill();
        b.setBillMonth(this.month);
        b.setBillYear(this.year);
        b.setCartrackerId("PT123456789");
        this.person2.addBill(b);
        
        this.roadUsage = new ArrayList<>();
        this.roadUsage.add(new RoadUsage("Rachelsmolen", RoadType.C, 5.00));
        this.roadUsage.add(new RoadUsage("Frederickplein", RoadType.A, 45.00));
    }*/
}
