package controller;

import domain.Bill;
import domain.Car;
import domain.ListBoxDate;
import domain.Person;
import domain.RoadType;
import dto.BillRoadUsage;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import service.BillService;
import service.CarPositionService;
import service.CarService;
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
    @EJB
    private PersonService personService;
    
    @EJB
    private BillService billService;
    
    @EJB
    private RateService rateService;
    
    @EJB
    private CarService carService;
    
    @EJB
    private CarPositionService positionService;
    
    @Inject
    private InvoiceSession session;

    private List<Bill> bills;
       
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
        Long personId = this.session.getPersonId();
        Person person = this.personService.findPersonById(personId);
        this.session.setPerson(person);

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
        this.bills = this.billService.generateBill(
                this.session.getPersonId(), this.month, this.year);
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
     * @param roadUsage type BillRoadUsage.
     * @return String rate.
     */
    public String getRate(BillRoadUsage roadUsage) {
        if (roadUsage.getRoadType() == RoadType.FOREIGN_COUNTRY_ROAD) {
            return "-";
        }
        
        Locale locale = new Locale("nl", "NL");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        return formatter.format(
                this.rateService.getRate(roadUsage.getRoadType()).getPrice());
    }

    /**
     * Get price for roadusage.
     * 
     * @param roadUsage type BillRoadUsage.
     * @return String price.
     */
    public String getPrice(BillRoadUsage roadUsage) {        
        Locale locale = new Locale("nl", "NL");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        return formatter.format(roadUsage.getPrice().doubleValue());
    }
    
    /**
     * Get fuel of the car with the given cartrackerId.
     * 
     * @param cartrackerId The cartracker id.
     * @return The name of the fuel type.
     */
    public String getFuel(String cartrackerId) {
        Car car = this.carService.getCar(cartrackerId);
        return car.getFuel().name();
    }

    /**
     * Get total price for bill.
     * 
     * @param bill bill reference to get price.
     * @return String total price bill.
     */
    public String getTotalPrice(Bill bill) {
        Locale locale = new Locale("nl", "NL");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        return formatter.format(bill.getTotalPrice());
    }

    public List<Bill> getBills() {
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
        return this.positionService.getCoordinates(cartrackerId, this.month, this.year);
    }
}
