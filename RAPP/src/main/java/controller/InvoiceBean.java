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
        String name = this.session.getPersonName();
        String address = this.session.getPersonAddress();
        String monthString = new DateFormatSymbols(Locale.ENGLISH)
                .getMonths()[this.session.getMonth()-1];
        
        String fileName = "invoice_" + bill.getCartrackerId() + "_" + 
                this.session.getYear() + "-" + this.session.getMonth() + ".pdf";
        File outputPath = new File("C:\\Proftaak\\invoices\\" + fileName);
        
        System.out.println("Generating pdf: " + fileName);
        
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
            PDImageXObject imageObject = PDImageXObject
                    .createFromFileByContent(brand, document);

            //Define the content stream
            //A4 size pixels 75 dpi: 620 x 877
            PDPageContentStream contentStream = 
                    new PDPageContentStream(document, page);
            
            //Draw brand image
            contentStream.drawImage(imageObject, 238F, 740F, 144F, 38F);
            
            //Keep track of yposition
            Float ypos = 700F;
            
            //Info
            this.writeText(contentStream, font, 11, 75F, ypos, "Name:");
            this.writeText(contentStream, font, 11, 175F, ypos, name);
            ypos -= 15F;
            this.writeText(contentStream, font, 11, 75F, ypos, "Address:");
            this.writeText(contentStream, font, 11, 175F, ypos, address);
            ypos -= 15F;
            this.writeText(contentStream, font, 11, 75F, ypos, "Cartracker:");
            this.writeText(contentStream, font, 11, 175F, ypos, 
                    bill.getCartrackerId());            
            ypos -= 15F;
            this.writeText(contentStream, font, 11, 75F, ypos, "Fuel:");
            this.writeText(contentStream, font, 11, 175F, ypos, 
                    this.getCar(bill.getCartrackerId()).getFuel().toString());
            ypos -= 15F;
            this.writeText(contentStream, font, 11, 75F, ypos, "Date:");
            this.writeText(contentStream, font, 11, 175F, ypos, 
                    monthString + " " + this.session.getYear());
            ypos -= 30F;
            
            //Invoice title
            this.writeText(contentStream, font, 16, 75F, ypos, "Invoice");
            ypos -= 25F;
            
            //Invoice
            this.writeText(contentStream, fontBold, 11, 75F, ypos, "RoadType");
            this.writeText(contentStream, fontBold, 11, 200F, ypos, "RoadName");
            this.writeText(contentStream, fontBold, 11, 325F, ypos, "Km");
            this.writeText(contentStream, fontBold, 11, 400F, ypos, "Price/km");
            this.writeText(contentStream, fontBold, 11, 475F, ypos, "Price");
            ypos -= 15F;
            
            if (bill.getRoadUsages().size() > 0) {                
                for (BillRoadUsage ru : bill.getRoadUsages()) {
                    Float yposLine = ypos + 11F;
                    contentStream.drawLine(75F, yposLine, 545F, yposLine);
                    this.writeText(contentStream, font, 11, 75F, ypos, 
                            this.getRoadType(ru));
                    this.writeText(contentStream, font, 11, 200F, ypos, 
                            ru.getRoadName());
                    this.writeText(contentStream, font, 11, 325F, ypos, 
                            this.getKm(ru));
                    this.writeText(contentStream, font, 11, 400F, ypos, 
                            this.getRate(ru));
                    this.writeText(contentStream, font, 11, 475F, ypos, 
                            this.getPrice(ru));
                    ypos -= 15F;
                }
            }
            
            Float yposLine = ypos + 11F;
            contentStream.drawLine(75F, yposLine, 545F, yposLine);
            this.writeText(contentStream, fontBold, 11, 75F, ypos, "Total:");  
            this.writeText(contentStream, font, 11, 475F, ypos, 
                    this.getTotalPrice(bill));
            
            //Close content stream
            contentStream.close();
            
            //Save the PDF
            OutputStream output = new FileOutputStream(outputPath); 
            document.save(output);
            document.close();
            output.close(); 
            
            InputStream input = new FileInputStream(outputPath);
            this.download(IOUtils.toByteArray(input), "application/pdf", 
                    fileName);
            
            System.out.println("Pdf done");
        } catch (IOException ex) {
            Logger.getLogger(InvoiceBean.class.getName())
                    .log(Level.SEVERE, null, ex);
        }        
    }

    /**
     * Write a line in the given pdf file.
     * @param contentStream pdf file.
     * @param font font style.
     * @param fontsize font size.
     * @param x pixels of page.
     * @param y pixels of page.
     * @param text text to write.
     * @throws IOException if process fails.
     */
    private void writeText(PDPageContentStream contentStream, PDFont font, 
            Integer fontsize, Float x, Float y, String text) 
            throws IOException {
        contentStream.beginText();
        contentStream.setFont(font, fontsize);
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
    }

    /**
     * Put file in downloads folder by sending response.
     * @param exportContent file to byte array.
     * @param contentType type of content.
     * @param fileName name of file.
     * @throws IOException if process fails.
     */
    public void download(byte[] exportContent, String contentType, 
            String fileName) throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        ec.responseReset(); 
        ec.setResponseContentType(contentType);
        ec.setResponseContentLength(exportContent.length);
        ec.setResponseHeader("Content-Disposition",
                "attachment; filename=\"" + fileName + "\"");

        OutputStream output = ec.getResponseOutputStream();        
        output.write(exportContent);

        fc.responseComplete();
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
