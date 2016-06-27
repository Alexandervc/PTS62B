/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Calendar;
import java.util.Date;

/**
 * Util for getting specific dates.
 * @author Alexander
 */
public class DateUtil {
    /**
     * Init the calendar for the given month and year.
     * @param month The month.
     * @param year The year.
     * @return The initialized calendar.
     */
    private static Calendar initCalendar(int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.add(Calendar.MONTH, -1);
        return cal;
    }
    
    /**
     * Get the first date of the given month in the given year.
     * @param month The month.
     * @param year The year.
     * @return The first date.
     */
    public static Date getFirstOfMonth(int month, int year) {
        Calendar cal = DateUtil.initCalendar(month, year);
        
        // changed calendar to cal
        cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE)); 
        cal.set(Calendar.HOUR_OF_DAY, 
                cal.getActualMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
        return cal.getTime();
    }

    /**
     * Get the last date of the given month and year.
     * @param month The month.
     * @param year The year.
     * @return The last date.
     */
    public static Date getLastOfMonth(int month, int year) {
        Calendar cal = DateUtil.initCalendar(month, year);
        
        // changed calendar to cal
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE)); 
        cal.set(Calendar.HOUR_OF_DAY, 
                cal.getActualMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
        return cal.getTime();
    }
}
