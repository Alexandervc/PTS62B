package controller;

import domain.Bill;
import domain.Car;
import domain.ListBoxDate;
import domain.Person;
import domain.RoadType;
import dto.BillRoadUsage;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
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

    private List<Bill> bills;
       
    //Current month and year.
    private int year;
    private int month;
    
    //Dates for combobox.
    private String dateIndex;
    private List<ListBoxDate> dates;
    
    //Locale Nederland
    private Locale locale;
    
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

        //Generate bills for person.
        this.bills = new ArrayList<>();
        this.generateBills();
        
        //Setup maps
        RequestContext requestContext = RequestContext.getCurrentInstance();  
        requestContext.execute("setDate(" + month + ", " + year + ")");
    }
    
    /**
     * Generate bill.
     */
    public void generateBills() {
        //Get all bills.
        this.bills = this.billService.generateBills(
                this.session.getPersonId(), this.month, this.year);
    }
    
    public void generatePdf(Bill bill) {
        String name = this.session.getPersonName();
        String address = this.session.getPerson().getAddress().toString();
        String fileName = "invoice_" + this.year + "-" + this.month + "_" + bill.getCartrackerId() + ".pdf";         
        File outputPath = new File("C:\\Proftaak\\invoices\\" + fileName);
        
        System.out.println("Generating pdf: " + fileName);
        System.out.println("Date generate: " + this.year + " - " + this.month);
        
        try {
            //Create pdf
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();

            //Adding page to document
            document.addPage(page);

            //Adding font to document
            PDFont font = PDType1Font.HELVETICA;
            PDFont fontBold = PDType1Font.HELVETICA_BOLD;
        
            //Retrieve image to be added to the PDF             
            File brand = new File("C:\\Proftaak\\rekeningrijden.png");
            PDImageXObject imageObject = PDImageXObject.createFromFileByContent(brand, document);

            //Define the content stream
            //A4 size pixels 75 dpi: 620 x 877
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            
            //Draw brand image
            contentStream.drawImage(imageObject, 238, 740, 144, 38);
            
            //Keep track of yposition
            Float ypos = 700F;
            
            //Info
            this.writeText(contentStream, font, 11, 75F, ypos, "Name:");
            this.writeText(contentStream, font, 11, 175F, ypos, name);
            ypos -= 15;
            this.writeText(contentStream, font, 11, 75F, ypos, "Address:");
            this.writeText(contentStream, font, 11, 175F, ypos, address);
            ypos -= 15;
            this.writeText(contentStream, font, 11, 75F, ypos, "Cartracker:");
            this.writeText(contentStream, font, 11, 175F, ypos, 
                    bill.getCartrackerId());            
            ypos -= 15;
            this.writeText(contentStream, font, 11, 75F, ypos, "Fuel:");
            this.writeText(contentStream, font, 11, 175F, ypos, 
                    this.getFuel(bill.getCartrackerId()));
            ypos -= 30;
            
            //Invoice title
            this.writeText(contentStream, font, 16, 75F, ypos, "Invoice");
            ypos -= 25;
            
            //Invoice
            this.writeText(contentStream, fontBold, 11, 75F, ypos, "RoadType");
            this.writeText(contentStream, fontBold, 11, 200F, ypos, "RoadName");
            this.writeText(contentStream, fontBold, 11, 325F, ypos, "Km");
            this.writeText(contentStream, fontBold, 11, 400F, ypos, "Price/km");
            this.writeText(contentStream, fontBold, 11, 475F, ypos, "Price");
            ypos -= 15;
            
            if (bill.getRoadUsages().size() > 0) {                
                for (BillRoadUsage ru : bill.getRoadUsages()) {

                    contentStream.drawLine(75, ypos+11, 545, ypos+11);
                    this.writeText(contentStream, font, 11, 75F, ypos, this.getRoadType(ru));
                    this.writeText(contentStream, font, 11, 200F, ypos, ru.getRoadName());
                    this.writeText(contentStream, font, 11, 325F, ypos, this.getKm(ru));
                    this.writeText(contentStream, font, 11, 400F, ypos, this.getRate(ru));
                    this.writeText(contentStream, font, 11, 475F, ypos, this.getPrice(ru));
                    ypos -= 15;
                }
            }
            
            contentStream.drawLine(75, ypos+11, 545, ypos+11);
            this.writeText(contentStream, fontBold, 11, 75F, ypos, "Total:");  
            this.writeText(contentStream, font, 11, 475F, ypos, this.getTotalPrice(bill));
            
            //Close content stream
            contentStream.close();
            
            //Save the PDF
            OutputStream output = new FileOutputStream(outputPath); 
            document.save(output);
            document.close();
            
            System.out.println("Pdf done");
        } catch (IOException ex) {
            Logger.getLogger(InvoiceBean.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void writeText(PDPageContentStream contentStream, PDFont font, 
            Integer fontsize, Float x, Float y, String text) 
            throws IOException {
        contentStream.beginText();
        contentStream.setFont(font, fontsize);
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
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
        
        System.out.println(this.year + " - " + this.month);
        
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
        return this.positionService.getCoordinates(cartrackerId, 
                this.month, this.year);
    }
}
