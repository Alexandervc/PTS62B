/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Edwin
 */
@Entity
@Table
public class ConnectionClient implements Serializable {

    @Id
    private Long id;
    
    private String name;
    
    @OneToMany
    @JoinColumn(name="CONNECTIONCLIENT_ID", referencedColumnName="ID")
    private List<Method> methods;

    /**
     * Empty constructor for JPA.
     */
    public ConnectionClient() {
    }
    
    public final Long getId() {
        return this.id;
    }

    public final void setId(Long id) {
        this.id = id;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final List<Method> getMethods() {
        return new ArrayList<>(this.methods);
    }

    public final void setMethods(List<Method> methods) {
        this.methods = new ArrayList<>(methods);
    }
      
}
