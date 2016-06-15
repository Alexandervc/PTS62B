package controller;

import dto.ListBoxDate;
import dto.PersonDto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Session bean to manage invoicebean data.
 * 
 * @author Linda
 */
@Named
@SessionScoped
public class InvoiceSession implements Serializable {
    private Long personId;
    private PersonDto person;
    
    //Current month and year.
    private int year;
    private int month;

    //Dates for combobox.
    private String dateIndex;
    private List<ListBoxDate> dates;
    
    @Inject
    private LanguageBean language;
    
    @PostConstruct
    public void setup() {
        setupDates(language.getLocale());
    }
    
    /**
     * Setup dates.
     * @param locale
     */
    public void setupDates(Locale locale) {
        //Current date.
        GregorianCalendar cal = new GregorianCalendar();
        this.year = cal.get(GregorianCalendar.YEAR);
        this.month = cal.get(GregorianCalendar.MONTH) + 1;
        this.dateIndex = "0";

        //Create list with ListBoxDate's.
        this.dates = new ArrayList<>();

        for (int m = 0; m < 25; m++) {
            GregorianCalendar mCal = new GregorianCalendar();
            mCal.add(Calendar.MONTH, -m);
            int mYear = mCal.get(Calendar.YEAR);
            String mMonthString = mCal.getDisplayName(
                    Calendar.MONTH, Calendar.LONG, locale);

            //Add date to list.
            String index = Integer.toString(m);
            String value = mMonthString + " " + mYear;            
            this.dates.add(new ListBoxDate(value, index));            
        } 
    }

    public Long getPersonId() {
        return this.personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }
    public void setPerson(PersonDto dto){
        this.person = dto;
    }
    
    public String restLinkCars(){
        return this.person.getLinkCarDto();
    }
    
    /**
     * Get full name of person.
     * @return String of name.
     */
    public String getPersonName(){
        return this.person.getFirstName() + " " + this.person.getInitials() +
                " " + this.person.getLastName();
    }
    
    /**
     * Get full Address of person.
     * @return String of address.
     */
    public String getPersonAddress(){
        return this.person.getAddress().getStreetname() + " " + 
                this.person.getAddress().getHousenumber() +
                ", " + this.person.getAddress().getZipcode() +" "+
                this.person.getAddress().getCity();
    }
    
    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
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
