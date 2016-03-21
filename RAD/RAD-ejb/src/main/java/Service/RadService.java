package Service;

import DAO.BillDAO;
import DAO.PersonDAO;
import Domain.Bill;
import Domain.Person;
import Domain.RoadType;
import Domain.RoadUsage;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Linda
 */
@Stateless
@LocalBean
public class RadService {
    @Inject
    private BillDAO billDAO;
    
    @Inject
    private PersonDAO personDAO;

    public RadService() {
        
    }

    public void test(){
        Bill b = new Bill();
        RoadUsage road = new RoadUsage(1L, "AutoWeg", RoadType.A, 25.36);
        List<RoadUsage> roads = new ArrayList<>();
        roads.add(road);
        b.setRoadUsage(roads);        
        billDAO.create(b);
        
        Person p = new Person();
        p.setName("Test");
        p.setCartracker(9L);
        p.addBill(b);
        personDAO.create(p);
    }
}
