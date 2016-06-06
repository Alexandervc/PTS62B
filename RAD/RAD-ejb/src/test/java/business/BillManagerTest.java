/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dao.BillDao;
import dao.RateDao;
import domain.Address;
import domain.Bill;
import domain.Person;
import domain.Rate;
import domain.RoadType;
import dto.BillRoadUsage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test for BillManager.
 *
 * @author Linda.
 */
@RunWith(MockitoJUnitRunner.class)
public class BillManagerTest {
    private static final String CARID1 = "PT123456789";
    
    private BillManager billManager;

    @Mock
    private BillDao billDao;

    @Mock
    private ForeignCountryManager countryManager;

    @Mock
    private RateDao rateDao;

    private Person person;
    private Bill bill1;
    
    private Integer monthToday;
    private Integer yearToday;
    
    public BillManagerTest() {
        // Empty for JPA.
    }

    /**
     * Setup method for test.
     */
    @Before
    public void setUp() {
        this.billManager = new BillManager();
        this.billManager.setBillDao(this.billDao);
        this.billManager.setForeignCountryManager(this.countryManager);
        this.billManager.setRateDAO(this.rateDao);

        this.createBillPerson();
    }

    /**
     * Test find bill.
     */
    @Test
    public void findBills() {
        this.billManager.findBills(this.person);
        verify(this.billDao, Mockito.times(1)).findAllForUser(this.person);
    }
    
    /**
     * Test generate Bill with save in database.
     */
    @Test
    public void generateBillCreate(){
        
        Rate rate = new Rate(1.00, RoadType.A);
        // Define when
        when(this.rateDao.find(RoadType.A))
                .thenReturn(rate);
        when(this.billDao.findBillWithCartracker(this.bill1.getCartrackerId()
                , this.monthToday, this.yearToday))
                .thenReturn(null);
        
        Bill bill3 = this.billManager.generateBill(this.person, 
                this.bill1.getRoadUsages(), CARID1, this.monthToday, 
                this.yearToday);
        
        verify(this.billDao, Mockito.times(1)).create(bill3);
    }
    
    /**
     * Test generate Bill with edit Bill.
     */
    @Test
    public void generateBillEdit(){
        
        Rate rate = new Rate(1.00, RoadType.A);
        // Define when
        when(this.rateDao.find(RoadType.A))
                .thenReturn(rate);
        when(this.billDao.findBillWithCartracker(this.bill1.getCartrackerId()
                , this.monthToday, this.yearToday))
                .thenReturn(this.bill1);
        
        Bill bill3 = this.billManager.generateBill(this.person, 
                this.bill1.getRoadUsages(), CARID1, this.monthToday, 
                this.yearToday);
        
        verify(this.billDao, Mockito.times(1)).edit(bill3);
    }

    private void createBillPerson() {
        String firstname = "Linda";
        String lastname = "van Engelen";
        String initials = "LMJC";

        String username = "LindaVanEngelen";
        String password = "admin";
        
        String streetname = "Sibeliuslaan";
        String number = "83";
        String zipcode = "5654CV";
        String city = "Eindhoven";

        Address adres = new Address(streetname, number, zipcode, city);
        this.person = new Person(firstname, lastname, initials,
                username, password, adres);

        List<BillRoadUsage> roadusages = new ArrayList<>();
        roadusages.add(new BillRoadUsage("A", RoadType.A, 5.21));
        Date billdate = new Date();
        this.monthToday = billdate.getMonth()+1;
        this.yearToday = billdate.getYear() + 1900;
        this.bill1 = new Bill(this.person, roadusages, 5.21,
                CARID1, this.monthToday, this.yearToday);
    }
}
