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

    private double rate;

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
     * @param rate double.
     * @param type RoadType.
     */
    public Rate(double rate, RoadType type) {
        this.rate = rate;
        this.type = type;
    }

    /**
     * Getter RoadType.
     *
     * @return RoadType.
     */
    public RoadType getType() {
        return this.type;
    }

    /**
     * Setter RoadType.
     *
     * @param type RoadType.
     */
    public void setType(RoadType type) {
        this.type = type;
    }

    /**
     * Getter Rate.
     *
     * @return rate double
     */
    public double getRate() {
        return this.rate;
    }

    /**
     * Setter Rate.
     *
     * @param rate double.
     */
    public void setRate(double rate) {
        this.rate = rate;
    }

}
