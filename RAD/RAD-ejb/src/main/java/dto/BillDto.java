/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A data transfer object for the Bill class.
 * @author Jesse
 */
public class BillDto implements Serializable {
    private String cartrackerId;
    private List<BillRoadUsage> roadUsages;
    private double totalPrice;
    private boolean paid;
    private int month;
    private int year;
    
    /**
     * Instantiates the BillDto class.
     * @param cartrackerId The cartrackerId of the cartracker for which this
     *      bill was generated.
     * @param roadUsages A list of RoadUsages which contain the movements.
     * @param totalPrice The total price of this bill.
     * @param paid A boolean describing if the bill was paid by the owner of the
     *      car.
     * @param month The month of the bill.
     * @param year The year of the bill.
     */
    public BillDto(String cartrackerId,
                   List<BillRoadUsage> roadUsages,
                   double totalPrice,
                   boolean paid,
                   int month,
                   int year) {
        this.cartrackerId = cartrackerId;
        this.roadUsages = new ArrayList<>(roadUsages);
        this.totalPrice = totalPrice;
        this.paid = paid;
        this.month = month;
        this.year = year;
    }
    
    public String getCartrackerId() {
        return this.cartrackerId;
    }

    public void setCartrackerId(String cartrackerId) {
        this.cartrackerId = cartrackerId;
    }

    public List<BillRoadUsage> getRoadUsages() {
        return new ArrayList<>(this.roadUsages);
    }

    public void setRoadUsages(List<BillRoadUsage> roadUsages) {
        this.roadUsages = new ArrayList<>(roadUsages);
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isPaid() {
        return this.paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
}
