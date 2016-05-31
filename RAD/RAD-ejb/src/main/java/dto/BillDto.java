/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.List;

/**
 * A data transfer object for the Bill class.
 * @author Jesse
 */
public class BillDto {
    private String cartrackerId;
    private List<BillRoadUsage> roadUsages;
    private double totalPrice;
    private boolean paid;
    private int month;
    private int year;
    
    public BillDto() { }

    public String getCartrackerId() {
        return cartrackerId;
    }

    public void setCartrackerId(String cartrackerId) {
        this.cartrackerId = cartrackerId;
    }

    public List<BillRoadUsage> getRoadUsages() {
        return roadUsages;
    }

    public void setRoadUsages(List<BillRoadUsage> roadUsages) {
        this.roadUsages = roadUsages;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
}
