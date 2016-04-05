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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSException;
import service.jms.JMSRADSender;

/**
 *
 * @author Linda
 */
@Singleton
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
    private JMSRADSender radSender;

    // TODO ?
    private CountDownLatch countDownLatch;

    //?
    //private List<RoadUsage> roadUsages;
    private Bill bill;

    //@Inject
    //private RmiClient rmiClient;
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
        Bill generatedBill = null;

        if (this.bill != null) {
            generatedBill = this.bill;
            this.bill = null;
        } else {

            try {
                /*if (this.countDownLatch == null) {
                this.countDownLatch = new CountDownLatch(1);
            }*/
                //try {
                //List<IRoadUsage> roadUsages = rmiClient.generateRoadUsages(cartrackerId, begin, end);
                //roadUsages.sort(null);
                //Bill bill = billManager.generateBill(person, roadUsages);
                //return bill;
                //} catch (RemoteException ex) {
                //    Logger.getLogger(RadService.class.getName()).log(Level.SEVERE, null, ex);
                //}
                radSender.sendGenerateRoadUsagesCommand(cartrackerId, begin, end);

                //this.countDownLatch.await();
                //while (this.roadUsages == null) {
                // wait
                //}
                //bill = new Bill(new Person("name"), this.roadUsages, 1);
                //this.roadUsages = null;
                //this.countDownLatch = null;
                generatedBill = new Bill(new Person("name"), new ArrayList<RoadUsage>(), 1);
            } catch (JMSException ex) {
                Logger.getLogger(RadService.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }

        return generatedBill;
    }

    public void receiveRoadUsages(List<RoadUsage> roadUsages) {
        //if (this.roadUsages == null) {
        // this.roadUsages = roadUsages;
        //}
        bill = new Bill(new Person("name"), roadUsages, 1);
        System.out.println(bill);
        /*if (this.countDownLatch != null) {
            countDownLatch.countDown();
        }*/
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
