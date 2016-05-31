package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Bill class.
 *
 * @author Linda.
 */
public class Bill implements Serializable {

    private Long id;

    private Person person;

    private List<RoadUsage> roadUsages;

    private double totalPrice;
    private boolean paid;
    private String cartrackerId;
    private int billMonth;
    private int billYear;

    /**
     * Empty constructor.
     *
     * @deprecated contructor for JPA.
     */
    @Deprecated
    public Bill() {
        // Empty for JPA.
        this.roadUsages = new ArrayList<>();
    }

    /**
     * Contructor.
     *
     * @param person type Peson.
     * @param roadUsages List Roadusage.
     * @param totalPrice double.
     * @param cartrackerId Long.
     * @param month int.
     * @param year int.
     */
    public Bill(Person person, List<RoadUsage> roadUsages, double totalPrice,
           String cartrackerId, int month, int year) {
        this.person = person;
        this.person.addBill(this);
        this.roadUsages = new ArrayList<>(roadUsages);
        this.totalPrice = totalPrice;
        this.paid = false;
        this.cartrackerId = cartrackerId;
        this.billMonth = month;
        this.billYear = year;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<RoadUsage> getRoadUsages() {
        return new ArrayList<>(this.roadUsages);
    }

    public void setRoadUsages(List<RoadUsage> roadUsages) {
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
