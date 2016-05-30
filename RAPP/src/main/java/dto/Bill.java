/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author Jesse
 */
public class Bill {

    private Long id;
    // TODO: Need person?
//    private Person person;
    // TODO: Check if trancient
//    private List<RoadUsage> roadUsages;

    private double totalPrice;
    private boolean paid;
    private String cartrackerId;
    private int billMonth;
    private int billYear;

    public Bill() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Person getPerson() {
//        return this.person;
//    }
//
//    public void setPerson(Person person) {
//        this.person = person;
//    }

//    public List<RoadUsage> getRoadUsages() {
//        return new ArrayList<>(this.roadUsages);
//    }
//
//    public void setRoadUsages(List<RoadUsage> roadUsages) {
//        this.roadUsages = new ArrayList<>(roadUsages);
//    }

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

    public String getCartrackerId() {
        return this.cartrackerId;
    }
    
    public void setCartrackerId(String cartrackerId) {
        this.cartrackerId = cartrackerId;
    }

    public int getBillMonth() {
        return this.billMonth;
    }
    
    public void setBillMonth(int billMonth) {
        this.billMonth = billMonth;
    }

    public int getBillYear() {
        return this.billYear;
    }
    
    public void setBillYear(int billYear) {
        this.billYear = billYear;
    }
}