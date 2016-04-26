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
 * Bill class.
 *
 * @author Linda.
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "bill.findAllForUser",
            query = "SELECT b FROM Bill b WHERE b.person2 = :person")
})
public class Bill implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Person person2;

    @Transient
    private List<RoadUsage> roadUsages;

    private double totalPrice;
    private boolean paid;
    private String cartrackerId;
    private String billMonth;
    private String billYear;

    /**
     * Empty constructor.
     *
     * @deprecated contructor for JPA.
     */
    @Deprecated
    public Bill() {
    }

    /**
     * Contructor.
     *
     * @param person type Peson.
     * @param roadUsages List Roadusage.
     * @param totalPrice double.
     * @param cartrackerId Long.
     * @param month String.
     * @param year String.
     */
    public Bill(Person person, List<RoadUsage> roadUsages, double totalPrice,
           String cartrackerId, String month, String year) {
        this.person2 = person;
        this.person2.addBill(this);
        List<RoadUsage> roadUsages_copy = roadUsages;
        this.roadUsages = roadUsages_copy;
        this.totalPrice = totalPrice;
        this.paid = false;
        this.cartrackerId = cartrackerId;
        this.billMonth = month;
        this.billYear = year;
    }

    /**
     * Getter Id.
     *
     * @return Id type Long.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Setter Id.
     *
     * @param id type Long.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter person.
     *
     * @return person2 type Person.
     */
    public Person getPerson2() {
        return this.person2;
    }

    /**
     * Setter person.
     *
     * @param person type Person.
     */
    public void setPerson2(Person person) {
        this.person2 = person;
    }

    /**
     * Getter RoadUsages.
     *
     * @return List RoadUsages.
     */
    public List<RoadUsage> getRoadUsages() {
        return this.roadUsages;
    }

    /**
     * Setter RoadUsages.
     *
     * @param roadUsages List RoadUsage.
     */
    public void setRoadUsages(List<RoadUsage> roadUsages) {
        List<RoadUsage> roadUsages_copy = roadUsages;
        this.roadUsages = roadUsages_copy;
    }

    /**
     * Getter Total Price.
     *
     * @return Total price double.
     */
    public double getTotalPrice() {
        return this.totalPrice;
    }

    /**
     * Setter Total Price.
     *
     * @param totalPrice double.
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Getter Paid.
     * @return boolean paid.
     */
    public boolean isPaid() {
        return this.paid;
    }

    /**
     * Setter paid.
     * @param paid boolean.
     */
    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    /**
     * Getter Cartrackerid.
     * @return Cartrackerid Long.
     */
    public String getCartrackerId() {
        return cartrackerId;
    }
    
    /**
     * Setter CartrackerId.
     * @param cartrackerId Long.
     */
    public void setCartrackerId(String cartrackerId) {
        this.cartrackerId = cartrackerId;
    }

    /**
     * Getter bill month.
     * @return bill String.
     */
    public String getBillMonth() {
        return this.billMonth;
    }
    /**
     * Setter bill month.
     * @param billMonth String. 
     */
    public void setBillMonth(String billMonth) {
        this.billMonth = billMonth;
    }

    /**
     * Getter Bill year.
     * @return bill String.
     */
    public String getBillYear() {
        return this.billYear;
    }
    
    /**
     * Setter bill year.
     * @param billYear String.
     */
    public void setBillYear(String billYear) {
        this.billYear = billYear;
    }

}
