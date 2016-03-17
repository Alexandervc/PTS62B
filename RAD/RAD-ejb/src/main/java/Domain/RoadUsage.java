/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Linda
 */
@Entity (name = "RoadUsage")
public class RoadUsage implements Serializable {
    @Id
    private Long id;
    
    @Column(name ="RoadName")
    private String name;
    
    @Column(name ="RoadType")
    private RoadType type;
    
    @Column(name ="Distance")
    private double km;

    public RoadUsage(Long id, String name, RoadType type, double km) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.km = km;
    }
}
