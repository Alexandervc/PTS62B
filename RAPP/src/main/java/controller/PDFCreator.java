package controller;

import dto.BillDto;
import dto.BillRoadUsage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormatSymbols;
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
/**
 * Class to handle pdf creation.
 * @author Melanie
 */
@Named
@RequestScoped
public class PDFCreator {
    private static final Logger LOGGER 
            = Logger.getLogger(PDFCreator.class.getName());
    
    @Inject
    private InvoiceSession session;
    
    @Inject
    private InvoiceBean invoice;
    
    //PDF variables.
    private static final String BASE_PATH = "C:\\Proftaak\\invoices\\";
    private static final String BRAND_PATH = "C:\\Proftaak\\rekeningrijden.png";
    private static final PDFont FONT = PDType1Font.HELVETICA;
    private static final PDFont FONT_BOLD = PDType1Font.HELVETICA_BOLD;
    private Float ypos;
    
    /**
     * Generate pdf for given bill.
     * @param bill choosen bill.
     */
    public void generatePdf(BillDto bill) {
        //Get info for bill.
        String name = this.session.getPersonName();
        String address = this.session.getPersonAddress();
        String month_string = new DateFormatSymbols(Locale.ENGLISH)
                .getMonths()[this.session.getMonth()-1];
        String timestamp = month_string + " " + this.session.getYear();
        
        //Setup output file.
        String fileName = String.format("invoice_%s_%s-%s.pdf",
		bill.getCartrackerId(), this.session.getYear(), 
                this.session.getMonth());
        File outputFile = new File(BASE_PATH + fileName);
        
        LOGGER.log(Level.INFO, null, "Generating pdf: " + fileName);
        
        try {
            //Create PDF.
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();

            //Adding page to document.
            document.addPage(page);             

            //Define the content stream.
            //A4 size pixels 75 dpi: 620 x 877.
            try (PDPageContentStream contentStream = 
                    new PDPageContentStream(document, page)) {
                //Draw brand image.
                File brand = new File(BRAND_PATH);
                PDImageXObject imageObject = PDImageXObject
                        .createFromFileByContent(brand, document);
                contentStream.drawImage(imageObject, 238F, 740F, 144F, 38F);

                //Keep track of yposition.
                this.ypos = 700F;

                //Write header.             
                this.writeHeader(contentStream, name, address, 
                        bill.getCartrackerId(), timestamp);                

                //Write table for invoice.
                this.writeTable(contentStream, bill);
            }
            
            //Save the PDF.
            try (OutputStream output = new FileOutputStream(outputFile)) {
                document.save(output);
                document.close();
            }
            
            try (InputStream input = new FileInputStream(outputFile)) {
                this.download(IOUtils.toByteArray(input), "application/pdf", 
                        fileName);
            }
            
            LOGGER.log(Level.INFO, null, "Pdf done");
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }        
    }

    /**
     * Write a line in the given pdf file.
     * @param contentStream pdf file.
     * @param font FONT style.
     * @param fontsize FONT size.
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
     * 
     * @param contentStream PDPageContentStream.
     * @param name String of name.
     * @param address String of address.
     * @param cartrackerId String of cartrackerId.
     * @param timestamp String of timestamp.
     * @throws IOException if process fails.
     */
    private void writeHeader(PDPageContentStream contentStream, String name,
            String address, String cartrackerId, String timestamp) 
            throws IOException {
        Integer fontsize = 11;
        Float xposTitle = 75F;
        Float xposValue = 175F;
        Float breakHeight = 15F;
        
        //Info header.
        this.writeText(contentStream, PDFCreator.FONT, fontsize, xposTitle, 
                this.ypos, "Name:");
        this.writeText(contentStream, PDFCreator.FONT, fontsize, xposValue, 
                this.ypos, name);
        this.breakLine(breakHeight);
        this.writeText(contentStream, PDFCreator.FONT, fontsize, xposTitle, 
                this.ypos, "Address:");
        this.writeText(contentStream, PDFCreator.FONT, fontsize, xposValue, 
                this.ypos, address);
        this.breakLine(breakHeight);
        this.writeText(contentStream, PDFCreator.FONT, fontsize, xposTitle,
                this.ypos, "Cartracker:");
        this.writeText(contentStream, PDFCreator.FONT, fontsize, xposValue, 
                this.ypos, cartrackerId);
        this.breakLine(breakHeight);
        this.writeText(contentStream, PDFCreator.FONT, fontsize, xposTitle, 
                this.ypos, "Fuel:");
        this.writeText(contentStream, PDFCreator.FONT, fontsize, xposValue, 
                this.ypos, this.invoice.getCar(cartrackerId).getFuel()
                        .toString());
        this.breakLine(breakHeight);
        this.writeText(contentStream, PDFCreator.FONT, fontsize, xposTitle, 
                this.ypos, "Date:");
        this.writeText(contentStream, PDFCreator.FONT, fontsize, xposValue, 
                this.ypos, timestamp);
        
        breakHeight = 30F;
        this.breakLine(breakHeight);

        //Invoice title.
        fontsize = 16;
        breakHeight = 25F;
        this.writeText(contentStream, PDFCreator.FONT, fontsize, xposTitle, 
                this.ypos, "Invoice");
        this.breakLine(breakHeight);
    }
    
    /**
     * Write table for bill.
     * @param contentStream PDPageContentStream.
     * @param bill given bill.
     * @throws IOException if process fails.
     */
    private void writeTable(PDPageContentStream contentStream, BillDto bill) 
            throws IOException {
        Integer fontsize = 11;
        Float breakHeight = 15F;
        Float lineXStart = 75F;
        Float lineXEnd = 545F;
        
        Float xposColumn1 = 75F;
        Float xposColumn2 = 200F;
        Float xposColumn3 = 325F;
        Float xposColumn4 = 400F;
        Float xposColumn5 = 475F;
        
        this.writeText(contentStream, PDFCreator.FONT_BOLD, fontsize, 
                xposColumn1, this.ypos, "RoadType");
        this.writeText(contentStream, PDFCreator.FONT_BOLD, fontsize, 
                xposColumn2, this.ypos, "RoadName");
        this.writeText(contentStream, PDFCreator.FONT_BOLD, fontsize, 
                xposColumn3, this.ypos, "Km");
        this.writeText(contentStream, PDFCreator.FONT_BOLD, fontsize, 
                xposColumn4, this.ypos, "Price/km");
        this.writeText(contentStream, PDFCreator.FONT_BOLD, fontsize, 
                xposColumn5, this.ypos, "Price");
        this.breakLine(breakHeight);

        //Write line for each roadUsage.
        if (bill.getRoadUsages().size() > 0) {
            for (BillRoadUsage ru : bill.getRoadUsages()) {
                Float yposLine = ypos + fontsize;

                //Draw line.
                contentStream.drawLine(lineXStart, yposLine, lineXEnd, 
                        yposLine);

                //Write values.
                this.writeText(contentStream, PDFCreator.FONT, fontsize, 
                        xposColumn1, this.ypos, this.invoice.getRoadType(ru));
                this.writeText(contentStream, PDFCreator.FONT, fontsize, 
                        xposColumn2, this.ypos, ru.getRoadName());
                this.writeText(contentStream, PDFCreator.FONT, fontsize, 
                        xposColumn3, this.ypos, this.invoice.getKm(ru));
                this.writeText(contentStream, PDFCreator.FONT, fontsize, 
                        xposColumn4, this.ypos, this.invoice.getRate(ru));
                this.writeText(contentStream, PDFCreator.FONT, fontsize, 
                        xposColumn5, this.ypos, this.invoice.getPrice(ru));
                this.breakLine(breakHeight);
            }
        }

        //Draw line.
        Float yposLine = this.ypos + fontsize;
        contentStream.drawLine(lineXStart, yposLine, lineXEnd, yposLine);

        //Write total value.
        this.writeText(contentStream, PDFCreator.FONT_BOLD, fontsize, 
                xposColumn1, this.ypos, "Total:");
        this.writeText(contentStream, PDFCreator.FONT, fontsize, 
                xposColumn5, this.ypos, this.invoice.getTotalPrice(bill));
    }
    
    /**
     * Break line in PDF.
     * @param height height of break.
     */
    private void breakLine(Float height) {
        this.ypos -= height;
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
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        externalContext.responseReset(); 
        externalContext.setResponseContentType(contentType);
        externalContext.setResponseContentLength(exportContent.length);
        externalContext.setResponseHeader("Content-Disposition",
                "attachment; filename=\"" + fileName + "\"");

        try (OutputStream output = externalContext.getResponseOutputStream()) {        
            output.write(exportContent);
        }

        facesContext.responseComplete();
    }
}
