package junitests;

import data.ResourceProducer;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.junit.Before;
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

    /**
     * Empty constructor for sonarqube.
     */
    public DBtest() {
        // Comment for sonarqube.
    }
    
    /**
     * Creates the entity manager on start up.
     * @throws NamingException Throws naming Exception if Entity 
     * Manager Factory name is not found.
     */
    @Before
    public void setUp() throws NamingException {
        this.em = Persistence.createEntityManagerFactory("Proftaak")
                .createEntityManager();  
    }

    /**
     * Tests if the database is able to connect.
     */
    @Test
    public void databaseTest() {
        this.em.getTransaction().begin();
    } 
}
