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
import dto.RoadUsage;
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
    private Bill bill;
    
    private Integer month;
    private Integer year;

    public BillManagerTest() {
        // Empty for JPA.
    }

    @Before
    public void setUp() {
        this.billManager = new BillManager();
        this.billManager.setBillDao(this.billDao);
        this.billManager.setForeignCountryManager(this.countryManager);
        this.billManager.setRateDAO(this.rateDao);

        this.createBillPerson();
    }


    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void findBills() {
        this.billManager.findBills(this.person);
        verify(this.billDao, Mockito.times(1)).findAllForUser(this.person);
    }

    @Test
    public void createBill() {
        this.billManager.createBill(this.bill);
         verify(this.billDao, Mockito.times(1)).create(bill);
    }
    
    @Test
    public void generateBill(){
        
        Rate rate = new Rate(1.00, RoadType.A);
        // Define when
        when(this.rateDao.find(RoadType.A))
                .thenReturn(rate);
        
        this.billManager.generateBill(this.person, this.bill.getRoadUsages(), 
                CARID1, this.month, this.year);
    }

    private void createBillPerson() {
        String firstname = "Linda";
        String lastname = "van Engelen";
        String initials = "LMJC";

        String streetname = "Sibeliuslaan";
        String number = "83";
        String zipcode = "5654CV";
        String city = "Eindhoven";

        Address adres = new Address(streetname, number, zipcode, city);
        this.person = new Person(firstname, lastname, initials, adres);

        List<RoadUsage> roadusages = new ArrayList<>();
        roadusages.add(new RoadUsage("A", RoadType.A, 5.21));
        Date billdate = new Date();
        this.month = billdate.getMonth();
        this.year = billdate.getYear() + 1900;
        this.bill = new Bill(this.person, roadusages, 8.21,
                CARID1, this.month, this.year);
    }
}
