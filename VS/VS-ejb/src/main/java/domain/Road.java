/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Alexander
 */
@Entity
public class Road implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id; 
    
    private String name;
    
    public Road() {
        
    }
    
    /**
     * 
     * @param name cannot be or empty
     */
    public Road(String name) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("name null");
        }
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
