package controller;

import dto.BillDto;
import domain.RoadType;
import dto.BillRoadUsage;
import dto.CarDto;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.primefaces.context.RequestContext;
import service.rest.clients.BillClient;

/**
 * Request scoped bean for invoice page.
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
    
    @Inject
    private PDFCreator pdfCreator;

    private List<BillDto> bills;
    private List<CarDto> cars;
    
    //Locale Nederland
    private Locale locale;

    /**
     * Setup application data. Load a person with personid from url. Get all
     * related data.
     */
    public void setup() {
        //Set locale
        this.locale = new Locale("nl", "NL");

        //Generate carsfor person.
        this.generateCars();
        this.bills = new ArrayList<>();
        this.generateBills();
        
        //Setup maps
        RequestContext requestContext = RequestContext.getCurrentInstance();  
        requestContext.execute("setDate(" + this.session.getMonth() + ", " + 
                this.session.getYear() + ")");
    }

    /**
     * Generate bills.
     */
    public void generateBills() {
        this.bills = new ArrayList<>();
        //Get all bills.
        for (CarDto car : this.cars) {
            this.bills.add(this.client.getBill(car.getCartrackerId(), 
                    this.session.getMonth(), this.session.getYear()));
        }
    }

    /**
     * Generate cars.
     */
    public void generateCars() {
        //Get all bills.
        this.cars = this.client.getCars(this.session.restLinkCars());
    }
    
    /**
     * Generate pdf for given bill.
     * @param bill choosen bill.
     */
    public void generatePdf(BillDto bill) {        
        this.pdfCreator.generatePdf(bill);
    }
    
    /**
     * Get bills for car.
     * @param cartracker id.
     * @return list of bills.
     */
    public List<BillDto> getBillsFromCar(String cartracker){
        List<BillDto> temp = new ArrayList<>();
        for(BillDto bill : this.bills){
            if(bill.getCartrackerId().equals(cartracker)){
                temp.add(bill);
                return temp;
            }
        }
        return new ArrayList<>();
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
        
        //Setup maps
        RequestContext requestContext = RequestContext.getCurrentInstance();  
        requestContext.execute("setDate(" + this.session.getMonth() + ", " + 
                this.session.getYear() + ")");
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

    /**
     * Get all bills.
     * @return list of bills.
     */
    public List<BillDto> getBills() {
        return new ArrayList<>(this.bills);
    }

    /**
     * Get all cars.
     * @return list of cars.
     */
    public List<CarDto> getCars() {
        return new ArrayList<>(this.cars);
    }
    
    /**
     * Get car for cartracker id.
     * @param cartrackerId id.
     * @return cardto class.
     */
    public CarDto getCar(String cartrackerId) {
        for (CarDto car : this.getCars()) {
            if (car.getCartrackerId().equals(cartrackerId)) {
                return car;
            }
        }
        
        return null;
    }
}
