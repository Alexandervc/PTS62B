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
import dto.RoadUsage;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Service for bill.
 *
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
     * Generate the bill for the given user and the given month.
     *
     * @param userId The user to generate a bill for.
     * @param month The month to generate the bill for.
     * @param year The year to generate the bill for.
     * @return The List of bills specific month and year.
     */
    public List<Bill> generateBill(Long userId, int month, int year) {

        // find person for bill
        Person person = this.personService.findPersonById(userId);
        if (person == null) {
            throw new IllegalArgumentException("user not found");
        }

        List<Bill> carBills = new ArrayList<>();
        // foreach car in person
        for (Car c : person.getCars()) {
            Boolean exists = false;
            // ask roadUsages from VS
            List<dto.RoadUsage> roadUsages = this.roadUsageService.
                    getRoadUsages(c.getCartrackerId(), month, year);

            // search if bill exists
            for (Bill b : person.getBills()) {
                // if equals cartrackerid, billMonth and billYear
                if (b.getCartrackerId().equals(c.getCartrackerId())
                        && b.getBillMonth() == month
                        && b.getBillYear() == year) {
                    // set RoadUsages
                    b.setRoadUsages(roadUsages);
                    // add to list carBills
                    carBills.add(b);
                    // set exists to true
                    exists = true;
                }
            }

            // if bill doesn't exists, create new Bill in Database
            if (!exists) {
                if (!roadUsages.isEmpty()) {
                    Bill newBill = this.billManager.generateBill(person,
                            roadUsages, c.getCartrackerId(), month, year);
                    // add to list carBills
                    carBills.add(newBill);
                } else {
                    carBills.add(new Bill(person, roadUsages, 0.00,
                            c.getCartrackerId(), month, year));
                }
            }
        }
        // return list carBills
        return carBills;
    }

    /**
     * Setter BillManager.
     *
     * @param billManager object.
     */
    public void setBillManager(BillManager billManager) {
        this.billManager = billManager;
    }

    /**
     * Setter PersonService.
     *
     * @param personService object.
     */
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Setter RoadUsageService.
     *
     * @param roadUsageService object.
     */
    public void setRoadUsageService(RoadUsageService roadUsageService) {
        this.roadUsageService = roadUsageService;
    }

}
