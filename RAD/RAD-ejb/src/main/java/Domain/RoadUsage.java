package domain;

import java.io.Serializable;

/**
 *
 * @author Linda
 */
public class RoadUsage implements Serializable {    
    private Long id;
    private String name;
    private RoadType type;
    private double km;

    public RoadUsage(Long id, String name, RoadType type, double km) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.km = km;
    }
    
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public RoadType getType() {
        return type;
    }

    public double getKm() {
        return km;
    }
}
