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
     * Generate bills for each car of the given user and the given month.
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
            // ask roadUsages from VS
            List<dto.BillRoadUsage> roadUsages = this.roadUsageService.
                    getRoadUsages(c.getCartrackerId(), month, year);

            // generate bill exists
            Bill b = this.billManager.generateBill(person,
                    roadUsages, c.getCartrackerId(), month, year);
            carBills.add(b);

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
