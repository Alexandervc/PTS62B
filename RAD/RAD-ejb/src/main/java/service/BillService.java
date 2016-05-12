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
     * Generate the bill for the given user and the given month.
     *
     * @param userId The user to generate a bill for.
     * @param month The month to generate the bill for.
     * @param year The year to generate the bill for.
     * @return The List of bills specific month and year.
     */
    public List<Bill> generateBill(Long userId, int month, int year) {
        List<Bill> carBills = new ArrayList<>();
        
        // find person for bill
        Person person = this.personService.findPersonById(userId);
        if (person == null) {
            throw new IllegalArgumentException("user not found");
        }

        // foreach car in person
        for(Car c : person.getCars()){
            Boolean exists = false;
            // ask roadUsages from VS
            List<dto.RoadUsage> roadUsages = this.roadUsageService.
                getRoadUsages(c.getCartrackerId(), month, year);
            
            // search if bill exists
            for(Bill b : person.getBills()){
                // if equals cartrackerid, billMonth and billYear
                if(b.getCartrackerId().equals(c.getCartrackerId()) &&
                        b.getBillMonth() == month &&
                        b.getBillYear() == year)
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
