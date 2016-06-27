package controller;

import domain.Bill;
import domain.Car;
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
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import service.BillService;
import service.CarPositionService;
import service.CarService;
import service.PersonService;

/**
 * Request scoped bean for invoice page.
 * 
 * @author Melanie
 */
@Named
@RequestScoped
public class InvoiceBean {
    private static final Logger LOGGER 
            = Logger.getLogger(InvoiceBean.class.getName());
    
    @EJB
    private PersonService personService;
    
    @EJB
    private BillService billService;
    
    @EJB
    private CarService carService;
    
    @EJB
    private CarPositionService positionService;
    
    @Inject
    private InvoiceSession session;
    
    @Inject
    private PDFCreator pdfCreator;

    private List<Bill> bills;
    
    //Locale Nederland.
    private Locale locale;  

    //PDF variables.
    private static final String BASE_PATH = "C:\\Proftaak\\invoices\\";    
    
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
        
        //Set locale.
        this.locale = new Locale("nl", "NL");

        //Generate bills for person.
        this.bills = new ArrayList<>();
        this.generateBills();
        
        //Setup maps.
        RequestContext requestContext = RequestContext.getCurrentInstance();  
        requestContext.execute("setDate(" + this.session.getMonth() + ", " + 
                this.session.getYear() + ")");
    }
    
    /**
     * Generate bill.
     */
    public void generateBills() {
        //Get all bills.
        this.bills = this.billService.generateBills(this.session.getPersonId(), 
                this.session.getMonth(), this.session.getYear());
    }
    
    /**
     * Generate pdf for given bill.
     * @param bill choosen bill.
     */
    public void generatePdf(Bill bill) {        
        this.pdfCreator.generatePdf(bill);
    }
    
    /**
     * Listener for date dropdown menu.
     */
    public void changeDate() {
        int index = Integer.parseInt(this.session.getDateIndex());
        
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MONTH, -index);
        this.session.setYear(cal.get(Calendar.YEAR));
        this.session.setMonth(cal.get(Calendar.MONTH) + 1);
        
        //Get all bills.
        this.generateBills();
        
        //Setup maps.
        RequestContext requestContext = RequestContext.getCurrentInstance();  
        requestContext.execute("setDate(" + this.session.getMonth() + ", " + 
                this.session.getYear() + ")");
        requestContext.execute("setupEvents()");
    }
    
    /**
     * Get roadtype for roadusage.
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

        NumberFormat formatter = NumberFormat.getCurrencyInstance(this.locale);
        return formatter.format(roadUsage.getRate().doubleValue());
    }

    /**
     * Get price for roadusage.
     * 
     * @param roadUsage type BillRoadUsage.
     * @return String price.
     */
    public String getPrice(BillRoadUsage roadUsage) {     
        NumberFormat formatter = NumberFormat.getCurrencyInstance(this.locale);
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
        NumberFormat formatter = NumberFormat.getCurrencyInstance(this.locale);
        return formatter.format(bill.getTotalPrice());
    }

    /**
     * Get all bills.
     * @return list of bills.
     */
    public List<Bill> getBills() {
        return new ArrayList<>(this.bills);
    }
    
    /**
     * Get coordinates for cartracker id.
     * @param cartrackerId id.
     * @return coordinates in json format.
     */
    public String getCoordinates(String cartrackerId) {
        return this.positionService.getCoordinates(cartrackerId, 
                this.session.getMonth(), this.session.getYear());
    }
}
