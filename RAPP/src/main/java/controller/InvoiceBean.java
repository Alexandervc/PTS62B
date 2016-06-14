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
import dto.CarDto;
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
import org.primefaces.context.RequestContext;
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
    private List<CarDto> cars;
    //Current month and year.
    private int year;
    private int month;

    //Dates for combobox.
    private String dateIndex;
    private List<ListBoxDate> dates;
    
    //Locale Nederland
    private Locale locale;

    /**
     * Setup application data. Load a person with personid from url. Get all
     * related data.
     */
    public void setup() {
        //Set locale
        this.locale = new Locale("nl", "NL");

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
                    Calendar.MONTH, Calendar.LONG, this.locale);

            //Add date to list.
            String index = Integer.toString(m);
            String value = m_month_string + " " + m_year;
            this.dates.add(new ListBoxDate(value, index));
        }

        //Generate carsfor person.
        this.generateCars();
        this.bills = new ArrayList<>();
        this.generateBills();
        
        //Setup maps
        RequestContext requestContext = RequestContext.getCurrentInstance();  
        requestContext.execute("setDate(" + month + ", " + year + ")");
    }

    /**
     * Generate bills.
     */
    public void generateBills() {
        this.bills = new ArrayList<>();
        //Get all bills.
        for (CarDto car : this.cars) {
            this.bills.add(this.client.getBill(car.getCartrackerId(), 
                    this.month, this.year));
        }
    }

    /**
     * Generate cars.
     */
    public void generateCars() {
        //Get all bills.
        this.cars = this.client.getCars(this.session.restLinkCars());
    }
    
    public List<BillDto> getBillFromCar(String cartracker){
        List<BillDto> temp = new ArrayList<>();
        for(BillDto bill : this.bills){
            if(bill.getCartrackerId().equals(cartracker)){
                temp.add(bill);
                return temp;
            }
        }
        return null;
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
        
        //Setup maps
        RequestContext requestContext = RequestContext.getCurrentInstance();  
        requestContext.execute("setDate(" + month + ", " + year + ")");
        requestContext.execute("setupEvents()");
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
        
        NumberFormat formatter = NumberFormat.getCurrencyInstance(this.locale);
        return formatter.format(roadUsage.getRate().doubleValue());
    }

    /**
     * Get price for roadusage.
     *
     * @param roadUsage type RoadUsage.
     * @return String price.
     */
    public String getPrice(BillRoadUsage roadUsage) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(this.locale);
        return formatter.format(roadUsage.getPrice().doubleValue());
    }

    /**
     * Get total price for bill.
     *
     * @param bill bill reference to get price.
     * @return String total price bill.
     */
    public String getTotalPrice(BillDto bill) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(this.locale);
        return formatter.format(bill.getTotalPrice());
    }

    public List<BillDto> getBills() {
        return new ArrayList<>(this.bills);
    }

    public List<CarDto> getCars() {
        return new ArrayList<>(this.cars);
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
}
