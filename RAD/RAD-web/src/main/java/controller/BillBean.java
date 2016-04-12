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
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import service.RadService;
import service.RoadUsage;

/**
 *
 * @author Alexander
 */
@Named
@RequestScoped
public class BillBean {

    @EJB
    private RadService service;

    private Bill bill;
    private String name;
    private String date;

    private List<ListBoxDate> dates;
    private Calendar datePast;

    public List<ListBoxDate> getDates() {
        return this.dates;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BillBean() {
        this.bill = null;
        this.dates = new ArrayList<>();
        this.datePast = new GregorianCalendar();

        datePast.add(Calendar.YEAR, -2);
        Calendar temp = this.datePast;

        for (int m = 0; m < 25; m++) {
            String month = new SimpleDateFormat("MMM").format(temp.getTime());
            this.dates.add(new ListBoxDate(month + " " + temp.get(Calendar.YEAR),
                    Integer.toString(m)));
            temp.add(Calendar.MONTH, 1);
        }
        
        Collections.reverse(this.dates);
    }

    /*
    @PostConstruct
    public void start() {
        generateBill();
    }*/
    public Bill getBill() {
        return bill;
    }

    public void generateBill() {
        bill = null;
        try {
            // Calc begin date
            SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
            Calendar temp = this.datePast;
            temp.add(Calendar.YEAR, -2);
            temp.add(Calendar.MONTH, Integer.parseInt(date));

            String tempBeginDateString = 01 + "/"
                    + temp.get(Calendar.MONTH) + "/" + temp.get(Calendar.YEAR);

            // Calc end Date
            temp.add(Calendar.MONTH, 1);
            temp.set(Calendar.DATE, 1);
            temp.add(Calendar.DATE, -1);
            String tempEndDateString = temp.get(Calendar.DAY_OF_MONTH) + "/"
                    + temp.get(Calendar.MONTH) + "/" + temp.get(Calendar.YEAR);

            // Convert Calendar tot Date
            Date dateBegin = dateformat.parse(tempBeginDateString);
            Date dateEnd = dateformat.parse(tempEndDateString);

            // Get bill from service
            this.bill = service.generateRoadUsages(this.name, dateBegin, dateEnd);
        } catch (NumberFormatException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getRate(RoadUsage roadUsage) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(this.service.getRate(roadUsage.getRoadType()).getRate());
    }

    public String getPrice(RoadUsage roadUsage) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(roadUsage.getKm() * this.service.getRate(roadUsage.getRoadType()).getRate());
    }

    public String getTotalPrice() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(this.bill.getTotalPrice());
    }

    public String getPersonName() {
        if (this.bill.getPerson2() != null) {
            return this.bill.getPerson2().getInitials() + " " + this.bill.getPerson2().getLastName();
        } else {
            return "";
        }
    }

    /*
    public List<IRoadUsage> getRoadUsages() {
        return service.generateRoadUsages(1L, new Date(), new Date());
    }
     */
}
