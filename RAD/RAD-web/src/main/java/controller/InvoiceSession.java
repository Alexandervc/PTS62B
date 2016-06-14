package controller;

import domain.ListBoxDate;
import domain.Person;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Session bean to manage invoicebean data.
 * 
 * @author Melanie
 */
@Named
@SessionScoped
public class InvoiceSession implements Serializable {
    private Long personId;
    private Person person;
    
    //Current month and year.
    private int year;
    private int month;
    
    //Dates for combobox.
    private String dateIndex;
    private List<ListBoxDate> dates;
    
    @PostConstruct
    public void setup() {
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
    }
    
    /**
     * Get full person name.
     * 
     * @return String person name.
     */
    public String getPersonName() {
        return this.person.getFirstName() + " " +
                this.person.getInitials() + " " + 
                this.person.getLastName();
    }

    public Long getPersonId() {
        return this.personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
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
