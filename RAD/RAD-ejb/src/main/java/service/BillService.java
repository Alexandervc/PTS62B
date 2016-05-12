/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import business.BillManager;
import domain.Bill;
import domain.Car;
import domain.Person;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Service for bill.
 * @author Alexander
 */
@Stateless
public class BillService {
    @Inject
    private BillManager billManager;
    
    @Inject
    private PersonService personService;
    
    @Inject
    private RoadUsageService roadUsageService;
    
    /**
     * Add bill to database.
     *
     * @param bill type Bill.
     */
    public void addBill(Bill bill) {
        this.billManager.createBill(bill);
    }
    
    /**
     * Generate the bill for the given user between the given dates.
     *
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
        Person person = this.personService.findPersonByName(username);
        if (person == null) {
            throw new IllegalArgumentException("user not found");
        }

        // foreach car in person
        for(Car c : person.getCars()){
            Boolean exists = false;
            // ask roadUsages from VS
            List<dto.RoadUsage> roadUsages = this.roadUsageService.
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
}
