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
 *
 * @author Linda
 */
@Entity(name = "Bill")
@NamedQueries({
    @NamedQuery(name="bill.findAllForUser", query="SELECT b FROM Bill b WHERE b.person = :person")
}) 
public class Bill implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Person person;
    
    private Long cartrackerID;
    private String billMonth;
    private String billYear;
    
    @Transient
    private List<RoadUsage> roadUsages;    
    
    private double totalPrice;
    private boolean paid;

    @Deprecated
    public Bill() {        
    }
    
    public Bill(Person person, List<RoadUsage> roadUsages, double totalPrice, 
            Long cartrackerId, String month, String year) {
        this.person = person;
        this.person.addBill(this);        
        this.roadUsages = roadUsages;
        this.totalPrice = totalPrice;
        this.paid = false;
        this.cartrackerID = cartrackerId;
        this.billMonth = month;
        this.billYear = year;
    }

    public Long getId() {
        return id;
    }
    
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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
    public Long getCartrackerID() {
        return cartrackerID;
    }

    public String getBillMonth() {
        return billMonth;
    }

    public String getBillYear() {
        return billYear;
    }

}