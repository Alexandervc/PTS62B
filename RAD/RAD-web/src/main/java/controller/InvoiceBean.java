package controller;

import domain.Bill;
import domain.ListBoxDate;
import domain.Person;
import dto.RoadUsage;
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

/**
 * Request scoped bean for invoice page.
 * 
 * @author Melanie
 */
@Named
@RequestScoped
public class InvoiceBean {
    private static final Logger LOGGER = Logger
            .getLogger(BillBean.class.getName());

    @EJB
    private RadService service;
    
    private Long personId;
    private Person person;
    private List<Bill> bills;
    
    //Month from combobox
    private String date;
    
    //Dates for combobox
    private List<ListBoxDate> dates;
    
    //Date for begin of month
    private Calendar datePast;
    
    //Format to convert string to date
    private final SimpleDateFormat DATEFORMAT = 
            new SimpleDateFormat("dd/MM/yyyy");
    
    /**
     * Setup application data.
     * Load a person with personid from url.
     * Get all related data.
     */
    public void setup() {
        //Get person by personId
        this.person = this.service.findPersonById(personId);
        
        //Setup dates
        this.date = "0";
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
        
        //Generate bills for person
        this.bills = new ArrayList<>();
        this.generateBills();
    }
    
    public void generateBills() {
        // Calc begin date
        Calendar temp = this.datePast;
        temp.add(Calendar.YEAR, -2);
        temp.add(Calendar.MONTH, Integer.parseInt(this.date));

        String tempBeginDateString = "01" + "/"
                + temp.get(Calendar.MONTH) + "/" + temp.get(Calendar.YEAR);

        // Calc end Date
        temp.add(Calendar.MONTH, 1);
        temp.set(Calendar.DATE, 1);
        temp.add(Calendar.DATE, -1);
        String tempEndDateString = temp.get(Calendar.DAY_OF_MONTH) + "/"
                + temp.get(Calendar.MONTH) + "/" + temp.get(Calendar.YEAR);

        try {
            // Convert Calendar tot Date
            Date dateBegin = DATEFORMAT.parse(tempBeginDateString);
            Date dateEnd = DATEFORMAT.parse(tempEndDateString);
        } catch (ParseException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }        
        
        //Get all bills
        //this.bills = this.service.generateBill(this.name, dateBegin, dateEnd);
    }
    
    /**
     * Get rate for roadusage.
     * 
     * @param roadUsage type RoadUsage.
     * @return String rate.
     */
    public String getRate(RoadUsage roadUsage) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(this.service.getRate(roadUsage.getRoadType())
                .getRate());
    }

    /**
     * Get price for roadusage.
     * 
     * @param roadUsage type RoadUsage.
     * @return String price.
     */
    public String getPrice(RoadUsage roadUsage) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(roadUsage.getKm() * this.service
                .getRate(roadUsage.getRoadType()).getRate());
    }

    /**
     * Get total price for bill.
     * 
     * @param bill
     * @return String total price bill.
     */
    public String getTotalPrice(Bill bill) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(bill.getTotalPrice());
    }

    /**
     * Get full person name.
     * 
     * @return String person name.
     */
    public String getPersonName() {
        return this.person.getInitials() + " " + 
                this.person.getLastName();
    }

    public Long getPersonId() {
        return this.personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public List<Bill> getBills() {
        return new ArrayList<>(this.bills);
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ListBoxDate> getDates() {
        return new ArrayList<>(this.dates);
    }
}
