/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Represents a cartracker.
 * @author Alexander
 */
@Entity
public class Cartracker implements Serializable {
    @Id
    private Long id;
    
    @OneToMany (mappedBy = "cartracker")
    private List<CarPosition> positions;
    
    @Deprecated
    public Cartracker() {
        
    }
    
    /**
     * Represents a cartracker.
     * @param id The id for the cartracker.
     */
    public Cartracker(Long id) {
        this.id = id;
        this.positions = new ArrayList<>();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CarPosition> getPositions() {
        return new ArrayList<>(this.positions);
    }

    public void setPositions(List<CarPosition> positions) {
        this.positions = new ArrayList<>(positions);
    }
}
