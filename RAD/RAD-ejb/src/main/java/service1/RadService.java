/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service1;

import business1.CarManager;
import business1.BillManager;
import business1.PersonManager;
import business1.RateManager;
import domain1.Bill;
import domain1.FuelType;
import domain1.Person;
import domain1.Rate;
import domain1.RoadType;
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
import service1.jms.JMSRADSender;

/**
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

    public Person findPersonByName(String name) {
        person = personManager.findPersonByName(name);
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

    public Bill generateRoadUsages(String username, Date begin, Date end) {
        Bill generatedBill = null;

        if (this.bill != null) {
            generatedBill = this.bill;
            this.bill = null;
        } else {
            try {

                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM",
                        Locale.getDefault());
                month = dateFormat.format(begin);
                year = Integer.toString(begin.getYear() + 1900);

                this.person = this.findPersonByName(username);
                if (this.person == null) {
                    throw new IllegalArgumentException("user not found");
                }

                cartrackerId = person.getCars().get(0).getCartrackerId();

                radSender.sendGenerateRoadUsagesCommand(cartrackerId,
                        begin, end);
                generatedBill = new Bill();
            } catch (JMSException ex) {
                Logger.getLogger(RadService.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }

        return generatedBill;
    }

    public void receiveRoadUsages(List<RoadUsage> roadUsages) {
        Logger.getLogger(RadService.class.getName())
                        .log(Level.INFO, "person" + this.person);
        if (this.person.getBills().size() > 0) {
            for (Bill b : this.person.getBills()) {
                if (b.getBillMonth().equals(month) && b.getBillYear().equals(year)
                        && b.getCartrackerId().equals(cartrackerId)) {
                    b.setRoadUsages(roadUsages);
                    this.bill = b;
                    Logger.getLogger(RadService.class.getName())
                        .log(Level.INFO, "found bill");
                }
            }
        } else {
            Logger.getLogger(RadService.class.getName())
                        .log(Level.INFO, "generate new bill");
            this.bill = billManager.generateBill(person, roadUsages, cartrackerId,
                    month, year);
        }
    }

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
