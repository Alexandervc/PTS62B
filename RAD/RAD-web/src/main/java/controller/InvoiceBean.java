package controller;

import domain.Bill;
import domain.ListBoxDate;
import domain.Person;
import dto.RoadUsage;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import service.BillService;
import service.PersonService;
import service.RateService;

/**
 * Request scoped bean for invoice page.
 * 
 * @author Melanie
 */
@Named
@RequestScoped
public class InvoiceBean {
    private static final Logger LOGGER = Logger
            .getLogger(InvoiceBean.class.getName());

    @EJB
    private PersonService personService;
    
    @EJB
    private BillService billService;
    
    @EJB
    private RateService rateService;
    
    @Inject
    private InvoiceSession session;

    private List<Bill> bills;
    
    //Current month and year
    private int year;
    private int month;
    
    //Dates for combobox
    private String dateIndex;
    private List<ListBoxDate> dates;
    
    /**
     * Setup application data.
     * Load a person with personid from url.
     * Get all related data.
     */
    public void setup() {
        //Get person by personId
        Long personId = this.session.getPersonId();
        Person person = this.personService.findPersonById(personId);
        this.session.setPerson(person);

        //Setup dates
        //Current date
        GregorianCalendar cal = new GregorianCalendar();
        this.year = cal.get(GregorianCalendar.YEAR);
        this.month = cal.get(GregorianCalendar.MONTH) + 1;
        this.dateIndex = "0";

        //Create list with ListBoxDate's
        this.dates = new ArrayList<>();

        for (int m = 0; m < 25; m++) {
            GregorianCalendar m_cal = new GregorianCalendar();
            m_cal.add(Calendar.MONTH, -m);
            int m_year = m_cal.get(Calendar.YEAR);
            String m_month_string = m_cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);

            //Add date to list
            String index = Integer.toString(m);
            String value = m_month_string + " " + m_year;            
            this.dates.add(new ListBoxDate(value, index));            
        }        

        //Generate bills for person
        this.bills = new ArrayList<>();
        this.generateBills();
    }
    
    public void generateBills() {
        //Get all bills
        //this.bills = this.billService.generateBill(session.getPersonId(), this.month, this.year);
    }
    
    public void changeDate() {
        int index = Integer.parseInt(this.dateIndex);
        
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MONTH, -index);
        this.year = cal.get(Calendar.YEAR);
        this.month = cal.get(Calendar.MONTH) + 1;
        
        //Get all bills
        this.generateBills();
    }
    
    /**
     * Get rate for roadusage.
     * 
     * @param roadUsage type RoadUsage.
     * @return String rate.
     */
    public String getRate(RoadUsage roadUsage) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(this.rateService.getRate(roadUsage.getRoadType())
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
        return formatter.format(roadUsage.getKm() * this.rateService
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

    public List<Bill> getBills() {
        return new ArrayList<>(this.bills);
    }

    public String getDateIndex() {
        return dateIndex;
    }

    public void setDateIndex(String dateIndex) {
        this.dateIndex = dateIndex;
    }

    public List<ListBoxDate> getDates() {
        return new ArrayList<>(this.dates);
    }
}