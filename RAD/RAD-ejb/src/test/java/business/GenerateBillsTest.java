/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import domain.Address;
import domain.Bill;
import domain.Car;
import domain.FuelType;
import domain.Person;
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
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import service.BillService;
import service.PersonService;
import service.RoadUsageService;
import static org.mockito.Mockito.verify;

/**
 * Test for Generate bill with multiple cars.
 *
 * @author Linda.
 */
@RunWith(MockitoJUnitRunner.class)
public class GenerateBillsTest {

    private static final String CARID1 = "PT123456789";
    private static final String CARID2 = "PT987654321";
    
    private static final Long PERSONID1 = 1L;
    private static final Long PERSONID2 = 2L;
    
    private BillService billService;

    @Mock
    private RoadUsageService roadUsagesService;

    @Mock
    private BillManager billManager;

    @Mock
    private PersonService personService;

    private Person person1;
    private Person person2;

    private Date begin;

    private Integer month;
    private Integer year;

    private List<BillRoadUsage> roadUsage;

    public GenerateBillsTest() {
        // Empty for JPA.
    }

    @Before
    public void setUp() {
        this.billService = new BillService();
        // set managers and service with Mock
        this.billService.setBillManager(this.billManager);
        this.billService.setPersonService(this.personService);
        this.billService.setRoadUsageService(this.roadUsagesService);

        this.begin = new Date();

        // set month for bill
        this.month = this.begin.getMonth();
        // set year for bill
        this.year = this.begin.getYear() + 1900;

        this.createPerson();

    }

    @Test
    public void getmultipleNewBillsTest() {
        // Define when
        when(this.personService.findPersonById(PERSONID1))
                .thenReturn(this.person1);

        when(this.roadUsagesService.getRoadUsages(CARID1,
                this.month, this.year))
                .thenReturn(this.roadUsage);
        when(this.roadUsagesService.getRoadUsages(CARID2,
                this.month, this.year))
                .thenReturn(this.roadUsage);

        this.billService.generateBills(PERSONID1, this.month, this.year);
        verify(this.roadUsagesService, Mockito.times(1))
                .getRoadUsages(CARID1, this.month, this.year);
        verify(this.roadUsagesService, Mockito.times(1))
                .getRoadUsages(CARID2, this.month, this.year);

        verify(this.billManager, Mockito.times(1))
                .generateBill(this.person1, this.roadUsage, CARID1,
                        this.month, this.year);
        verify(this.billManager, Mockito.times(1))
                .generateBill(this.person1, this.roadUsage, CARID2,
                        this.month, this.year);
    }

    /**
     * Test get Multiple old bills.
     */
    @Test
    public void getmultipleOldBillsTest() {
        // Define when
        // Define when
        when(this.personService.findPersonById(PERSONID2))
                .thenReturn(this.person2);

        when(this.roadUsagesService.getRoadUsages(CARID1,
                this.month, this.year))
                .thenReturn(this.roadUsage);
        when(this.roadUsagesService.getRoadUsages(CARID2,
                this.month, this.year))
                .thenReturn(this.roadUsage);

        this.billService.generateBills(PERSONID2, this.month, this.year);
        verify(this.roadUsagesService, Mockito.times(1))
                .getRoadUsages(CARID1, this.month, this.year);
        verify(this.roadUsagesService, Mockito.times(1))
                .getRoadUsages(CARID2, this.month, this.year);

        verify(this.billManager, Mockito.times(1))
                .generateBill(this.person2, this.roadUsage,
                        CARID2, this.month, this.year);
        verify(this.billManager, Mockito.times(1))
                .generateBill(this.person2, this.roadUsage,
                        CARID1, this.month, this.year);
    }

    private void createPerson() {
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

        this.person1 = new Person(firstname, lastname, initials, username,
                password, adres);
        this.person1.setId(PERSONID1);

        this.person2 = new Person(firstname, lastname, initials, username,
                password, adres);
        this.person2.setId(PERSONID2);

        Car c1 = new Car(this.person1, CARID1, FuelType.Diesel);
        Car c2 = new Car(this.person1, CARID2, FuelType.Petrol);

        Car c3 = new Car(this.person2, CARID1, FuelType.Diesel);
        Car c4 = new Car(this.person2, CARID2, FuelType.Petrol);
        
        Bill b = new Bill();
        b.setBillMonth(this.month);
        b.setBillYear(this.year);
        b.setCartrackerId(CARID1);
        this.person2.addBill(b);

        this.roadUsage = new ArrayList<>();
        this.roadUsage.add(new BillRoadUsage("Rachelsmolen", RoadType.C,
                5.00));
        this.roadUsage.add(new BillRoadUsage("Frederickplein", RoadType.A,
                45.00));
    }
}
