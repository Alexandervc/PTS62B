package domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

/**
 *
 * @author Melanie.
 */
@Entity
public class Rate implements Serializable {
    @Id @Enumerated(EnumType.STRING)
    private RoadType type;
    
    private double rate;    
    
    @Deprecated
    public Rate() {        
    }
    
    public Rate(double rate, RoadType type) {
        this.rate = rate;
        this.type = type;
    } 
    
    public double getRate() {
        return rate;
    }
}