package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Linda
 */
@Entity (name = "Person")
public class Person implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "person")
    private List<Bill> bills;
    
    private Long cartracker;
    
    @Deprecated
    public Person() {        
    }

    public Person(String name) {
        this.name = name;
        this.bills = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public List<Bill> getBills() {
        return bills;
    }
    
    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }
    
    public Long getCartracker() {
        return cartracker;
    }

    public void setCartracker(Long cartracker) {
        this.cartracker = cartracker;
    }
    
    /**
     * add bill to list bills
     * @param b 
     */
    public void addBill(Bill b){
        this.bills.add(b);
        b.setPerson(this);
    }
}
