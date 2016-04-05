/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import business.CarManager;
import business.BillManager;
import business.PersonManager;
import business.RateManager;
import domain.Bill;
import domain.FuelType;
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

    @Inject
    private CarManager carManager;

    @Inject
    private RmiClient rmiClient;

    @PostConstruct
    public void start() {
    }

    public Person addPerson(String firstname, String lastname, String initials,
            String streetname, String number, String zipcode,
            String city, String country) {
        person = personManager.createPerson(firstname, lastname, initials,
                streetname, number, zipcode, city, country);
        return person;
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
    
    public void addCar(Person person, Long cartracker, FuelType fuel) {
        carManager.createCar(person, cartracker, fuel);
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

    public void setPersonManager(PersonManager personManager) {
        this.personManager = personManager;
    }

    public void setBillManager(BillManager billManager) {
        this.billManager = billManager;
    }

    public void setRateManager(RateManager rateManager) {
        this.rateManager = rateManager;
    }

    public void setCarManager(CarManager carManager) {
        this.carManager = carManager;
    }
}
