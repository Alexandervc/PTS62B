/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dto.RoadUsage;
import business.CarManager;
import business.BillManager;
import business.ForeignCountryManager;
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
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * RAD Service class.
 *
 * @author Linda.
 */
@Stateless
public class RadService {
    @Inject
    private PersonManager personManager;
    @Inject
    private BillManager billManager;
    @Inject
    private RateManager rateManager;

    @Inject
    private CarManager carManager;

    @Inject
    private RoadUsagesService roadUsagesService;

    @Inject
    private ForeignCountryManager foreignCountryManager;

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
        return this.personManager.createPerson(firstname, lastname,
                initials, streetname, number, zipcode, city, country);
    }

    /**
     * Find person in database with name can be null.
     *
     * @param name String.
     * @return found person.
     */
    public Person findPersonByName(String name) {
        return this.personManager.findPersonByName(name);
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
     * Generate the bill for the given user between the given dates.
     * @param username The user to generate a bill for.
     * @param begin The begin date of the period to generate the bill for.
     * @param end The end date of the period to generate the bill for.
     * @return The generated bill.
     */
    public Bill generateBill(String username, Date begin, Date end) {
        // format date
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM",
                Locale.getDefault());
        // set month for bill
        String month = dateFormat.format(begin);
        // set year for bill
        String year = Integer.toString(begin.getYear() + 1900);

        // find person for bill
        Person person = this.findPersonByName(username);
        if (person == null) {
            throw new IllegalArgumentException("user not found");
        }

        // set cartracker id for bill
        String cartrackerId = person.getCars().get(0).getCartrackerId();

        // ask roadUsages from VS
        List<RoadUsage> roadUsages = this.roadUsagesService.
                getRoadUsages(cartrackerId, begin, end);

        // generate empty temp bill
        return this.billManager.generateBill(person,
                roadUsages, cartrackerId, month, year);
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
     *
     * @param roadUsages The roadUsages to get the price for.
     * @return The price.
     */
    public Double getTotalPrice(List<RoadUsage> roadUsages) {
        return this.billManager.calculatePrice(roadUsages);
    }

    /**
     * Add a foreign country ride to the database, this stores the total price
     * of the ride.
     *
     * @param foreignCountryRideId The id of the foreign country ride, this id
     * is set in VS when the message is received from the central system.
     * @param totalPrice The total price of the foreign country ride.
     */
    public void addForeignCountryRide(
            Long foreignCountryRideId,
            double totalPrice) {
        this.foreignCountryManager.createForeignCountryRide(
                foreignCountryRideId,
                totalPrice);
    }

}
