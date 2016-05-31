/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dto.BillDto;
import dto.ListBoxDate;
import domain.RoadType;
import dto.BillRoadUsage;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import service.rest.clients.BillClient;

/**
 *
 * @author Linda
 */
@Named
@RequestScoped
public class InvoiceBean {   
    @Inject
    private InvoiceSession session;
    
    @Inject
    private BillClient client;

    private List<BillDto> bills;
    private Long personId;
    //Current month and year.
    private int year;
    private int month;
    
    //Dates for combobox.
    private String dateIndex;
    private List<ListBoxDate> dates;
    
    /**
     * Setup application data.
     * Load a person with personid from url.
     * Get all related data.
     */
    public void setup() {
        //Get person by personId.
        this.personId = this.session.getPersonId();

        //Setup dates.
        //Current date.
        GregorianCalendar cal = new GregorianCalendar();
        this.year = cal.get(GregorianCalendar.YEAR);
        this.month = cal.get(GregorianCalendar.MONTH) + 1;
        this.dateIndex = "0";

        //Create list with ListBoxDate's.
        this.dates = new ArrayList<>();

        for (int m = 0; m < 25; m++) {
            GregorianCalendar m_cal = new GregorianCalendar();
            m_cal.add(Calendar.MONTH, -m);
            int m_year = m_cal.get(Calendar.YEAR);
            String m_month_string = m_cal.getDisplayName(
                    Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);

            //Add date to list.
            String index = Integer.toString(m);
            String value = m_month_string + " " + m_year;            
            this.dates.add(new ListBoxDate(value, index));            
        }        

        //Generate bills for person.
        this.bills = new ArrayList<>();
        this.generateBills();
    }
    
    /**
     * Generate bill.
     */
    public void generateBills() {
        //Get all bills.
        this.bills = this.client.getBill(this.personId, month, year);
    }
    
    /**
     * Listener for date dropdown menu.
     */
    public void changeDate() {
        int index = Integer.parseInt(this.dateIndex);
        
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MONTH, -index);
        this.year = cal.get(Calendar.YEAR);
        this.month = cal.get(Calendar.MONTH) + 1;
        
        //Get all bills.
        this.generateBills();
    }
    
    /**
     * Get roadtype for roadusage
     * 
     * @param roadUsage.
     * @return roadtype string format.
     */
    public String getRoadType(BillRoadUsage roadUsage) {
        if (roadUsage.getRoadType() == RoadType.FOREIGN_COUNTRY_ROAD) {
            return "Foreign country road";
        }
        
        return roadUsage.getRoadType().toString();
    }
    
    /**
     * Get km with two decimals.
     * 
     * @param roadUsage.
     * @return kilometers with two decimals.
     */
    public String getKm(BillRoadUsage roadUsage) {
        if (roadUsage.getRoadType() == RoadType.FOREIGN_COUNTRY_ROAD) {
            return "-";
        }
                
        DecimalFormat formatter = new DecimalFormat("#.00"); 
        return formatter.format(roadUsage.getKm());
    }
    
    /**
     * Get rate for roadusage.
     * 
     * @param roadUsage type RoadUsage.
     * @return String rate.
     */
    public String getRate(BillRoadUsage roadUsage) {
        if (roadUsage.getRoadType() == RoadType.FOREIGN_COUNTRY_ROAD) {
            return "-";
        }
        
        Locale locale = new Locale("nl", "NL");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        //TODO: get roadtype price
        return formatter.format(0.00);
    }

    /**
     * Get price for roadusage.
     * 
     * @param roadUsage type RoadUsage.
     * @return String price.
     */
    public String getPrice(BillRoadUsage roadUsage) {        
        Locale locale = new Locale("nl", "NL");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        //TODO: calculate roadusage price
        return formatter.format(roadUsage.getKm() * 0.1);
    }
    
    /**
     * Get fuel of the car with the given cartrackerId.
     * 
     * @param cartrackerId The cartracker id.
     * @return The name of the fuel type.
     */
    public String getFuel(String cartrackerId) {
        //TODO: get car
        //Car car = this.carService.getCar(cartrackerId);
        return "";
    }

    /**
     * Get total price for bill.
     * 
     * @param bill bill reference to get price.
     * @return String total price bill.
     */
    public String getTotalPrice(BillDto bill) {
        Locale locale = new Locale("nl", "NL");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        return formatter.format(bill.getTotalPrice());
    }

    public List<BillDto> getBills() {
        return new ArrayList<>(this.bills);
    }

    public String getDateIndex() {
        return this.dateIndex;
    }

    public void setDateIndex(String dateIndex) {
        this.dateIndex = dateIndex;
    }

    public List<ListBoxDate> getDates() {
        return new ArrayList<>(this.dates);
    }
    
    public String getCoordinates(String cartrackerId) {
        //TODO: get positions
        return "";//this.positionService.getCoordinates(cartrackerId, this.month, this.year);
    }
}
