package junitests1;

import data1.ResourceProducer;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Edwin
 */
public class DBtest {
    
    private EntityManager em;
    private ResourceProducer producer;

    /**
     * Empty constructor for sonarqube.
     */
    public DBtest() {
    }
    
    @Before
    public void setUp() throws NamingException {
        producer = new ResourceProducer();
        this.em = Persistence.createEntityManagerFactory("Proftaak")
                .createEntityManager();  
    }

    @Test
    public void DatabaseTest() {
        this.em.getTransaction().begin();
    } 
}
