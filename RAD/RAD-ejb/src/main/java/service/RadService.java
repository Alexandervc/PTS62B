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
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import service.jms.RequestRoadUsagesBean;

/**
 * RAD Service class.
 * @author Linda.
 */
@Singleton
public class RadService {

    private Person person;
    private String cartrackerId;
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
    private RequestRoadUsagesBean radSender;

    private Bill bill;

    /**
     * Post construct method.
     */
    @PostConstruct
    public void start() {
        // empty post construct method.
    }

    /**
     * Add person to database.
     *
     * @param firstname String.
     * @param lastname String.
     * @param initials String.
     * @param streetname String.
     * @param number String.
     * @param zipcode String.
     * @param city String.
     * @param country String.
     * @return created person type Person.
     */
    public Person addPerson(String firstname, String lastname, String initials,
            String streetname, String number, String zipcode,
            String city, String country) {
        this.person = this.personManager.createPerson(firstname, lastname, 
                initials, streetname, number, zipcode, city, country);
        return this.person;
    }

    /**
     * Find person in database with name can be null.
     *
     * @param name String.
     * @return found person.
     */
    public Person findPersonByName(String name) {
        this.person = this.personManager.findPersonByName(name);
        return this.person;
    }

    /**
     * Add Rate to database.
     *
     * @param rate double.
     * @param type RoadType.
     */
    public void addRate(double rate, RoadType type) {
        this.rateManager.createRate(rate, type);
    }

    /**
     * Find Rate in database with roadType can be null.
     *
     * @param type of Road.
     * @return found Rate.
     */
    public Rate getRate(RoadType type) {
        return this.rateManager.findRate(type);
    }

    /**
     * Add bill to database.
     *
     * @param bill type Bill.
     */
    public void addBill(Bill bill) {
        this.billManager.createBill(bill);
    }

    /**
     * Add car to database.
     *
     * @param person otype Person.
     * @param cartracker id Long.
     * @param fuel fueltype.
     */
    public void addCar(Person person, String cartracker, FuelType fuel) {
        this.carManager.createCar(person, cartracker, fuel);
    }

    /**
     * Generate RoadUsages for bill.
     *
     * @param username String.
     * @param begin Date
     * @param end Date.
     * @return generated bill type Bill.
     */
    public Bill requestRoadUsages(String username, Date begin, Date end) {
        Bill generatedBill = null;

        if (this.bill != null) {
            // roadusages are received from VS
            generatedBill = this.bill;
            this.bill = null;
        } else {
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
        }
        return generatedBill;
    }

    /**
     * Receive RoadUsages from VS.
     *
     * @param roadUsages List RoadUsage.
     */
    public void receiveRoadUsages(List<RoadUsage> roadUsages) {
        // set local bill with correct fields
        this.bill = this.billManager.generateBill(this.person, roadUsages,
                this.cartrackerId, this.month, this.year);
    }

    /**
     * Set personManager for JUnittest.
     *
     * @param personManager PersonManager.
     */
    public void setPersonManager(PersonManager personManager) {
        this.personManager = personManager;
    }

    /**
     * Set BillManager for JUnittest.
     *
     * @param billManager BillManager.
     */
    public void setBillManager(BillManager billManager) {
        this.billManager = billManager;
    }

    /**
     * Set RateManager for JUnittest.
     *
     * @param rateManager RateManager.
     */
    public void setRateManager(RateManager rateManager) {
        this.rateManager = rateManager;
    }

    /**
     * Set carManager for JUnittest.
     *
     * @param carManager CarManager.
     */
    public void setCarManager(CarManager carManager) {
        this.carManager = carManager;
    }
    
    /**
     * Get the price for the given roadUsages.
     * @param roadUsages The roadUsages to get the price for.
     * @return The price.
     */
    public Double getTotalPrice(List<RoadUsage> roadUsages) {
        return this.billManager.calculatePrice(roadUsages);
    }
    
}
