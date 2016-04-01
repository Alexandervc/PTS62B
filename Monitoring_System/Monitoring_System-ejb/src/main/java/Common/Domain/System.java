/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common.Domain;

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
 *
 * @author Edwin
 */
@Entity
@NamedQueries({
@NamedQuery(query = "select s from System s", name = "get systems")
})
public class System implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Column(name = "IP")
    private String IP;
    
    @Column(name = "PORT")
    private int port;
    
    @OneToMany
    @JoinColumn(name="SYSTEM")
    private List<ConnectionClient> clients;
    
    @OneToMany
    @JoinColumn(name="SYSTEM_ID", referencedColumnName="ID")
    private List<Test> tests;

    public System() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }
    
    public int getPort() {
        return port;
    }
    
    public void setPort(int port) {
        this.port = port;
    }

    public List<Test> getTests() {
        return new ArrayList<>(tests);
    }

    public void setTests(List<Test> tests) {
        this.tests = new ArrayList<>(tests);
    }

    public List<ConnectionClient> getClients() {
        return new ArrayList<>(clients);
    }

    public void setClients(List<ConnectionClient> clients) {
        this.clients = new ArrayList<>(clients);
    }
    
    
}
