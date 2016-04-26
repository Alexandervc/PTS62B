/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * The entity for a system that is being monitored by the LMS system.
 * @author Edwin.
 */
@Entity
@NamedQueries({
    @NamedQuery(query = "select s from System s", name = "get systems"),
    @NamedQuery(query = "select s from System s where s.name = :name",
            name = "get system by name")
})
public class System implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "NAME", unique = true)
    private String name;
    
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Column(name = "IP")
    private String ip;
    
    @Column(name = "PORT")
    private int port;
    
    @OneToMany
    @JoinColumn(name="SYSTEM_ID", referencedColumnName="ID")
    private List<Test> tests;

    /**
     * Empty constructor for JPA usage.
     */
    public System() {
        // Comment needed for sonarqube.
    }

    /**
     * Creates a system, using parameters to set values and initialises
     * a new ArrayList for the tests.
     * @param name The name of the system.
     * @param description The description of the system.
     * @param ip The IP adress of the system.
     * @param port The port that is used for communication.
     */
    public System(String name, String description, String ip, int port) {
        this.name = name;
        this.description = description;
        this.ip = ip;
        this.port = port;
        this.tests = new ArrayList();
    }
    
    

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public void setPort(int port) {
        this.port = port;
    }

    public List<Test> getTests() {
        return new ArrayList<>(this.tests);
    }

    public void setTests(List<Test> tests) {
        this.tests = new ArrayList<>(tests);
    }
    
    /**
     * Adds a test to the list of tests, Sets the systemId of the test
     * to the id of this system.
     * @param test The test that has to be added.
     */
    public void addTest(Test test) {
        test.setSystemID(this.id);
        this.tests.add(test);
    }
    
    
}
