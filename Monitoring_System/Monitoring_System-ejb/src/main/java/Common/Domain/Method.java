/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.Domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class Method implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    
    @OneToMany
    @JoinColumn(name="MethodTest_ID", referencedColumnName="ID")
    private List<MethodTest> tests;
        
    public Method() {
    
    }

    public Method(String name) {
        this.name = name;
        this.tests = new ArrayList<>();
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

    public List<MethodTest> getTests() {
        return new ArrayList<>(this.tests);
    }

    public void setTests(List<MethodTest> tests) {
        this.tests = new ArrayList<>(tests);
    }
    
    
}
