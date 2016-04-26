/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;


import common.domain.Test;
import common.domain.TestType;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Dao used to store Test data in the database using JPA.
 * @author Edwin
 */
@Stateless
public class TestDao extends AbstractDao {

    @Inject
    @MonitoringDB2
    private EntityManager em;

    /**
     * Creates a TestDao using the extended AbstractDao.
     */
    public TestDao() {
        super(Test.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
    
    /**
     * Retrieves all tests of a specific type on a system.
     * @param system The system the test is required for.
     * @param type The testtype that is required.
     * @return The test that matches the requirements.
     */
    public List<Test> retrieveAllTestsForTypeForSystem(
            common.domain.System system,
            TestType type) {
        Query query = this.em
                .createNamedQuery("get tests for system with type");
        query.setParameter("systemId", system.getId());
        query.setParameter("type", type);
        return (List<Test>)query.getResultList();
    }
    
    /**
     * Retrieves the latest test of a specific type on a system.
     * @param system The system the test is required for.
     * @param type The testtype that is required.
     * @return The test that matches the requirements.
     */
    public Test retrieveLatestTestForTypeForSystem(
            common.domain.System system,
            TestType type) {
        Query query = this.em
                .createNamedQuery("get latest test for system with type");
        query.setParameter("systemId", system.getId());
        query.setParameter("type", type);
        query.setMaxResults(1);
        return (Test) query.getSingleResult();
    }
}
