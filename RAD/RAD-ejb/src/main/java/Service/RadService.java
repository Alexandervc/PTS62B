package service;

import business.BillManager;
import business.PersonManager;
import business.RateManager;
import domain.Bill;
import domain.Person;
import domain.Rate;
import domain.RoadType;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Linda
 */
@Stateless
public class RadService  {    
    
    @Inject
    private PersonManager personManager;
    
    @Inject
    private BillManager billManager;
    
    @Inject
    private RateManager rateManager;
    
    @Inject
    private RmiClient rmiClient;

    /*
    public RadService() {
        //Huidige gebruiker (mock voor inlogsysteem)
        person = personManager.addPerson("Melanie");
    }  */
    
    @PostConstruct
    public void start() {
        personManager.addPerson("Melanie");
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

    public List<IRoadUsage> generateRoadUsages(Long cartrackerId, Date begin, Date end) {
        try {
            return rmiClient.generateRoadUsages(cartrackerId, begin, end);
        } catch (RemoteException ex) {
            Logger.getLogger(RadService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
