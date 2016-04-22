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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.JMSException;
import service.jms.JMSRADSender;

/**
 * RAD Service class
 *
 * @author Linda
 */
@Singleton
public class RadService {

    private Person person;
    private Long cartrackerId;
    private String month;
    private String year;

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

    private Bill bill;

    /**
     * Post construct method
     */
    @PostConstruct
    public void start() {
        // empty post construct method
    }

    /**
     * add person to database
     *
     * @param firstname of person
     * @param lastname of person
     * @param initials of person
     * @param streetname of person
     * @param number of person
     * @param zipcode of person
     * @param city of person
     * @param country of person
     * @return created person
     */
    public Person addPerson(String firstname, String lastname, String initials,
            String streetname, String number, String zipcode,
            String city, String country) {
        this.person = this.personManager.createPerson(firstname, lastname, initials,
                streetname, number, zipcode, city, country);
        return this.person;
    }

    /**
     * find person in database with name can be null
     *
     * @param name of person
     * @return found person
     */
    public Person findPersonByName(String name) {
        this.person = this.personManager.findPersonByName(name);
        return this.person;
    }

    /**
     * add Rate to database
     *
     * @param rate of road
     * @param type of road
     */
    public void addRate(double rate, RoadType type) {
        this.rateManager.createRate(rate, type);
    }

    /**
     * find Rate in database with roadType can be null
     *
     * @param type of Road
     * @return found Rate
     */
    public Rate getRate(RoadType type) {
        return this.rateManager.findRate(type);
    }

    /**
     * add bill to database
     *
     * @param bill
     */
    public void addBill(Bill bill) {
        this.billManager.createBill(bill);
    }

    /**
     * add car to database
     *
     * @param person of car
     * @param cartracker id of car
     * @param fuel fueltype of car
     */
    public void addCar(Person person, Long cartracker, FuelType fuel) {
        this.carManager.createCar(person, cartracker, fuel);
    }

    /**
     * generate RoadUsages for bill
     *
     * @param username of person
     * @param begin startdate bill
     * @param end einddate bill
     * @return generated bill;
     */
    public Bill generateRoadUsages(String username, Date begin, Date end) {
        Bill generatedBill = null;

        if (this.bill != null) {
            // roadusages are received from VS
            generatedBill = this.bill;
            this.bill = null;
        } else {
            try {
                // format date
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM",
                        Locale.getDefault());
                // set month for bill
                this.month = dateFormat.format(begin);
                // set year for bill
                this.year = Integer.toString(begin.getYear() + 1900);

                // find person for bill
                this.person = this.findPersonByName(username);
                if (this.person == null) {
                    throw new IllegalArgumentException("user not found");
                }

                // set cartracker id for bill
                this.cartrackerId = this.person.getCars().get(0).getCartrackerId();

                // ask roadUsages from VS
                this.radSender.sendGenerateRoadUsagesCommand(this.cartrackerId,
                        begin, end);

                // generate empty temp bill
                generatedBill = new Bill();
            } catch (JMSException ex) {
                Logger.getLogger(RadService.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        return generatedBill;
    }

    /**
     * receive RoadUsages from VS
     *
     * @param roadUsages from VS
     */
    public void receiveRoadUsages(List<RoadUsage> roadUsages) {
        // set local bill with correct fields
        this.bill = this.billManager.generateBill(this.person, roadUsages, 
                this.cartrackerId, this.month, this.year);
    }

    /**
     * set personManager for JUnittest
     *
     * @param personManager
     */
    public void setPersonManager(PersonManager personManager) {
        this.personManager = personManager;
    }

    /**
     * set BillManager for JUnittest
     *
     * @param billManager
     */
    public void setBillManager(BillManager billManager) {
        this.billManager = billManager;
    }

    /**
     * set RateManager for JUnittest
     *
     * @param rateManager
     */
    public void setRateManager(RateManager rateManager) {
        this.rateManager = rateManager;
    }

    /**
     * set carManager for JUnittest
     *
     * @param carManager
     */
    public void setCarManager(CarManager carManager) {
        this.carManager = carManager;
    }
}
