package dto;

import java.io.Serializable;

/**
 * Dates for listbox.
 * @author Linda
 */
public class ListBoxDate implements Serializable {
     private String date;
     private String months;

    public ListBoxDate(String date, String months) {
        this.date = date;
        this.months = months;
    }

    public String getDate() {
        return this.date;
    }

    public String getMonths() {
        return this.months;
    }
    
}
