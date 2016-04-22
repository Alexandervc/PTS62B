package domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import service.RoadUsage;

/**
 * Bill class 
 * @author Linda
 */
@Entity
@NamedQueries({
    @NamedQuery(name="bill.findAllForUser", query="SELECT b FROM Bill b WHERE b.person2 = :person")
}) 
public class Bill implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Person person2;
    
    @Transient
    private List<RoadUsage> roadUsages;    
    
    private double totalPrice;
    private boolean paid;
    
    private Long cartrackerId;
    private String billMonth;
    private String billYear;

    @Deprecated
    public Bill() {        
    }
    
    public Bill(Person person, List<RoadUsage> roadUsages, double totalPrice,
           Long cartrackerId, String month, String year) {
        this.person2 = person;
        this.person2.addBill(this);        
        this.roadUsages = roadUsages;
        this.totalPrice = totalPrice;
        this.paid = false;
        this.cartrackerId=cartrackerId;
        this.billMonth = month;
        this.billYear = year;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRoadUsages(List<RoadUsage> roadUsages) {
        this.roadUsages = roadUsages;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setCartrackerId(Long cartrackerId) {
        this.cartrackerId = cartrackerId;
    }

    public void setBillMonth(String billMonth) {
        this.billMonth = billMonth;
    }

    public void setBillYear(String billYear) {
        this.billYear = billYear;
    }

    public boolean isPaid() {
        return paid;
    }

    public Long getId() {
        return id;
    }
    
    public Person getPerson2() {
        return person2;
    }

    public void setPerson2(Person person) {
        this.person2 = person;
    }
    
    public List<RoadUsage> getRoadUsages() {
        return roadUsages;
    }

    public boolean getPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Long getCartrackerId() {
        return cartrackerId;
    }

    public String getBillMonth() {
        return billMonth;
    }

    public String getBillYear() {
        return billYear;
    }
}