package domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

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

    //@Transient
    //private List<RoadUsage> roadUsages;

    private boolean paid;
    private double totalPrice;

    @Deprecated
    public Bill() {        
    }
    
    public Bill(Person person) {
        this.person = person;
        this.person.addBill(this);
        
        this.paid = false;
        this.totalPrice = 13.89;
        //this.roadUsages = new ArrayList<>();
        this.person = new Person();
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
    
    /*
    public List<RoadUsage> getRoadUsages() {
        return roadUsages;
    }

    public void setRoadUsage(List<RoadUsage> roadUsage) {
        this.roadUsages = roadUsage;
    }
    */

    public boolean getPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
