package domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import static jdk.nashorn.internal.objects.NativeMath.round;

/**
 *
 * @author Melanie
 */
@Entity (name = "Rate")
public class Rate implements Serializable {
    @Id @Enumerated(EnumType.STRING)
    private RoadType type;
    
    private double rate;    
    
    @Deprecated
    public Rate() {        
    }
    
    public Rate(double rate, RoadType type) {
        this.rate = round(rate, 2);
        this.type = type;
    } 
    
    public double getRate() {
        return rate;
    }
}
