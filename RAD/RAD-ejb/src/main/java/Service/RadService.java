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
    private Person person;
    
    @Inject
    private PersonManager personManager;
    
    @Inject
    private BillManager billManager;
    
    @Inject
    private RateManager rateManager;
    
    @Inject
    private RmiClient rmiClient;
    
    @PostConstruct
    public void start() {
        person = personManager.createPerson("Melanie");
    }
    
    public void addRate(double rate, RoadType type) {
        rateManager.createRate(rate, type);
    }
    
    public Rate getRate(RoadType type) {
        return rateManager.findRate(type);
    }
    
    public void addBill(Bill bill) {
        billManager.createBill(bill);
    }
    
    public Bill generateRoadUsages(Long cartrackerId, Date begin, Date end) {
        try {
            List<IRoadUsage> roadUsages = rmiClient.generateRoadUsages(cartrackerId, begin, end);
            roadUsages.sort(null);
            Bill bill = billManager.generateBill(person, roadUsages);
            return bill;
        } catch (RemoteException ex) {
            Logger.getLogger(RadService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    /*
    public List<IRoadUsage> generateRoadUsages(Long cartrackerId, Date begin, Date end) {
        try {
            return rmiClient.generateRoadUsages(cartrackerId, begin, end);
        } catch (RemoteException ex) {
            Logger.getLogger(RadService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    */
}
