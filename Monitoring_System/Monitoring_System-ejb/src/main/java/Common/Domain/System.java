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
 *
 * @author Edwin
 */
@Entity
@NamedQueries({
    @NamedQuery(query = "select s from System s", name = "get systems"),
    @NamedQuery(query = "select s from System s where s.name = :name", name = "get system by name")
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
    @JoinColumn(name="SYSTEM")
    private List<ConnectionClient> clients;
    
    @OneToMany
    @JoinColumn(name="SYSTEM_ID", referencedColumnName="ID")
    private List<Test> tests;

    /**
     * Empty constructor for JPA usage.
     */
    public System() {
    }

    public System(String name, String description, String ip, int port) {
        this.name = name;
        this.description = description;
        this.ip = ip;
        this.port = port;
        this.clients = new ArrayList<>();
        this.tests = new ArrayList();
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

    public final String getDescription() {
        return this.description;
    }

    public final void setDescription(String description) {
        this.description = description;
    }

    public final String getIp() {
        return this.ip;
    }

    public final void setIp(String ip) {
        this.ip = ip;
    }
    
    public final int getPort() {
        return this.port;
    }
    
    public final void setPort(int port) {
        this.port = port;
    }

    public final List<Test> getTests() {
        return new ArrayList<>(this.tests);
    }

    public final void setTests(List<Test> tests) {
        this.tests = new ArrayList<>(tests);
    }

    public final List<ConnectionClient> getClients() {
        return new ArrayList<>(this.clients);
    }

    public final void setClients(List<ConnectionClient> clients) {
        this.clients = new ArrayList<>(clients);
    }
    
    public void addTest(Test test) {
        test.setSystemID(this.id);
        this.tests.add(test);
    }
    
    
}
