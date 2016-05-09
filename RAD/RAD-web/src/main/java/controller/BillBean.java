/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domain.Bill;
import domain.ListBoxDate;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import service.RadService;
import dto.RoadUsage;

/**
 * Bean for RAD-web.
 *
 * @author Alexander.
 */
@Named
@RequestScoped
public class BillBean {

    private static final Logger LOGGER = Logger
            .getLogger(BillBean.class.getName());
    private final SimpleDateFormat DATEFORMAT = 
            new SimpleDateFormat("dd/MM/yyyy");
    
    @EJB
    private RadService service;

    private Bill bill;
    private String name;
    private String date;

    private List<ListBoxDate> dates;
    private Calendar datePast;

    /**
     * Constructor BillBean.
     */
    public BillBean() {
        this.bill = null;
        this.loadDates();
    }
    
    public void loadDates() {
        this.dates = new ArrayList<>();
        this.datePast = new GregorianCalendar();

        this.datePast.add(Calendar.YEAR, -2);
        Calendar temp = this.datePast;

        for (int m = 0; m < 25; m++) {
            String month = new SimpleDateFormat("MMM").format(temp.getTime());
            this.dates.add(new ListBoxDate(month + " "
                    + temp.get(Calendar.YEAR), Integer.toString(m)));
            temp.add(Calendar.MONTH, 1);
        }
        
        Collections.reverse(this.dates);
    }

    /**
     * Getter bill.
     *
     * @return bill type Bill.
     */
    public Bill getBill() {
        return bill;
    }

    /**
     * Getter name person.
     *
     * @return String name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter name person.
     *
     * @param name String.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter date.
     *
     * @return String date.
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Setter Date.
     *
     * @param date String.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Getter Dates for listbox.
     *
     * @return ArrayList ListBoxDate.
     */
    public List<ListBoxDate> getDates() {
        return new ArrayList<>(this.dates);
    }

    /**
     * Generate bill function.
     * Calculates Startdate and enddate.
     * Request send to RadService.
     */
    public void generateBill() {
        bill = null;
        try {
            // Calc begin date month
            Calendar temp = this.datePast;
            temp.add(Calendar.YEAR, -2);
            temp.add(Calendar.MONTH, Integer.parseInt(this.date));

            String tempBeginDateString = "01" + "/"
                    + temp.get(Calendar.MONTH) + "/" + temp.get(Calendar.YEAR);
            
            Date dateBegin = DATEFORMAT.parse(tempBeginDateString);

            // Calc end date month
            temp.add(Calendar.MONTH, 1);
            temp.set(Calendar.DATE, 1);
            temp.add(Calendar.DATE, -1);
            String tempEndDateString = temp.get(Calendar.DAY_OF_MONTH) + "/"
                    + temp.get(Calendar.MONTH) + "/" + temp.get(Calendar.YEAR);
            
            Date dateEnd = DATEFORMAT.parse(tempEndDateString);

            // Get bill from service
            this.bill = this.service.generateBill(this.name, 
                    dateBegin, dateEnd);
        } catch (NumberFormatException | ParseException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    /**
     * Getter Rate roadusage.
     * @param roadUsage type RoadUsage.
     * @return String rate.
     */
    public String getRate(RoadUsage roadUsage) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(this.service.getRate(roadUsage.getRoadType())
                .getRate());
    }

    /**
     * Getter price roadusage.
     * @param roadUsage type RoadUsage.
     * @return String price.
     */
    public String getPrice(RoadUsage roadUsage) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(roadUsage.getKm() * this.service
                .getRate(roadUsage.getRoadType()).getRate());
    }

    /**
     * Getter total price bill.
     * @return String total price bill.
     */
    public String getTotalPrice() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(this.bill.getTotalPrice());
    }

    /**
     * Getter person name from bill.
     * @return String person name.
     */
    public String getPersonName() {
        if (this.bill.getPerson2() != null) {
            return this.bill.getPerson2().getInitials() + " " + 
                    this.bill.getPerson2().getLastName();
        } else {
            return "";
        }
    }
}
