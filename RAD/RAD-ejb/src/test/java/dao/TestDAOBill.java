
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import business.BillManager;
import domain.Bill;
import domain.Person;
import domain.RoadType;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import service.RadService;
import dto.RoadUsage;

/**
 * Test bill Dao.
 *
 * @author Linda.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestDAOBill {

    private RadService service;

    @Mock
    private BillManager billManager;

    private Person person1;
    private Bill bill1;

    private static final double KM = 5.00;
    private static final String CARTRACKER = "PT123456789";
    private static final double PRICE = 10.35;

    /**
     * Constructor.
     */
    public TestDAOBill() {
        // Empty for start up test
    }

    /**
     * Do first after start test
     */
    @Before
    public void setUp() {
        this.service = new RadService();
        this.service.setBillManager(this.billManager);

        this.person1 = new Person("Linda", "van Engelen", "LMJC",
                "Sibeliuslaan", "83", "5654CV", "Eindhoven", "Nederland");

        List<RoadUsage> roadUsages = new ArrayList<>();
        roadUsages.add(new RoadUsage("Rachelsmolen", RoadType.C, KM));

        this.bill1 = new Bill(this.person1, roadUsages, PRICE,
                CARTRACKER, "april", "2016");
    }

    /**
     * Test adding bill.
     */
    @Test
    public void testAddBill() {
        this.service.addBill(this.bill1);
        verify(this.billManager, Mockito.times(1)).createBill(this.bill1);
    }

}
