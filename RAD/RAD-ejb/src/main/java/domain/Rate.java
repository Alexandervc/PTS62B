package domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

/**
 * Rate class.
 *
 * @author Melanie.
 */
@Entity
public class Rate implements Serializable {
    @Id
    @Enumerated(EnumType.STRING)
    private RoadType type;

    private double price;

    /**
     * Empty constructor.
     *
     * @deprecated contructor for JPA.
     */
    @Deprecated
    public Rate() {
        // Empty for JPA.
    }

    /**
     * Contructor.
     *
     * @param price double.
     * @param type RoadType.
     */
    public Rate(double price, RoadType type) {
        this.price = price;
        this.type = type;
    }

    public RoadType getType() {
        return this.type;
    }

    public void setType(RoadType type) {
        this.type = type;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double rate) {
        this.price = rate;
    }
}