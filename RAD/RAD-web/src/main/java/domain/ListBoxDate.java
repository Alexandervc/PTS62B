/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Linda
 */
public class ListBoxDate {
     private String date;
     private String months;

    public ListBoxDate(String date, String months) {
        this.date = date;
        this.months = months;
    }

    public String getDate() {
        return date;
    }

    public String getMonths() {
        return months;
    }
    
}
