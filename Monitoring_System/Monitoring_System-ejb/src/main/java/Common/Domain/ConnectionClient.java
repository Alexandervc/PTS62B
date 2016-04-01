/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common.Domain;

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

    public List<Method> getMethods() {
        return new ArrayList<>(methods);
    }

    public void setMethods(List<Method> methods) {
        this.methods = new ArrayList<>(methods);
    }
    
    
    
}
