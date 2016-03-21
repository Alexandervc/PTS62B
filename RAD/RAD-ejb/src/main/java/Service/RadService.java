package service;

import business.BillManager;
import business.PersonManager;
import business.RateManager;
import domain.Bill;
import domain.Person;
import domain.Rate;
import domain.RoadType;
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
    private Person person;
    
    @Inject
    private PersonManager personManager;
    
    @Inject
    private BillManager billManager;
    
    @Inject
    private RateManager rateManager;

    public RadService() {
        //Huidige gebruiker (mock voor inlogsysteem)
        person = personManager.addPerson("Melanie");
    }  
    
    public void addRate(double rate, RoadType type) {
        rateManager.addRate(rate, type);
    }

    /*
    public void test(){  
        Person p = new Person("Alexander");
        p.setCartracker(9L);
        personDAO.create(p);        
        
        Bill b = new Bill(person);
        //RoadUsage road = new RoadUsage(1L, "AutoWeg", RoadType.A, 25.36);
        //List<RoadUsage> roads = new ArrayList<>();
        //roads.add(road);
        //b.setRoadUsage(roads);        
        billDAO.create(b);        
    }
    */
}
