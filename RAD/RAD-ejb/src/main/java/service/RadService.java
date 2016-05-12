/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import business.BillManager;
import business.CarManager;
import business.ForeignCountryManager;
import business.PersonManager;
import business.RateManager;
import domain.Bill;
import domain.Car;
import domain.FuelType;
import domain.Person;
import domain.Rate;
import domain.RoadType;
import dto.RoadUsage;
import java.util.ArrayList;

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
     * Find person in database by personId.
     * 
     * @param personId id of person.
     * @return found person.
     */
    public Person findPersonById(Long personId) {
        return this.personManager.findPersonById(personId);
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
     * @return The List of bills specific month and year.
     */
    public List<Bill> generateBill(String username, Date begin, Date end) {
        List<Bill> carBills = new ArrayList<>();
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

        // foreach car in person
        for(Car c : person.getCars()){
            Boolean exists = false;
            // ask roadUsages from VS
            List<RoadUsage> roadUsages = this.roadUsagesService.
                getRoadUsages(c.getCartrackerId(), begin, end);
            
            // search if bill exists
            for(Bill b : person.getBills()){
                // if equals cartrackerid, billMonth and billYear
                if(b.getCartrackerId().equals(c.getCartrackerId()) &&
                        b.getBillMonth().equals(month) &&
                        b.getBillYear().equals(year))
                {
                    // set RoadUsages
                    b.setRoadUsages(roadUsages);
                    // add to list carBills
                    carBills.add(b);
                    // set exists to true
                    exists = true;
                }
            }
            
            // if bill doesn't exists, create new Bill in Database
            if(!exists){
                Bill newBill = this.billManager.generateBill(person,
                roadUsages, c.getCartrackerId(), month, year);
                // add to list carBills
                carBills.add(newBill);
            }
        }
        // return list carBills
        return carBills;
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
     *      is set in VS when the message is received from the central system.
     * @param totalPrice The total price of the foreign country ride.
     */
    public void addForeignCountryRide(
            String foreignCountryRideId,
            double totalPrice) {
        this.foreignCountryManager.createForeignCountryRide(
                foreignCountryRideId,
                totalPrice);
    }

}
