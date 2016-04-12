/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;


import common.Domain.Test;
import common.Domain.TestType;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Edwin
 */
@Stateless
public class TestDao extends AbstractDao {

    @Inject
    private @MonitoringDB2 EntityManager em;

    public TestDao() {
        super(Test.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public Test retrieveLatestTestForTypeForSystem(common.Domain.System system
            , TestType type) {
        Query query = this.em.createNamedQuery("get latest test for system with type");
        query.setParameter("systemId", system.getId());
        query.setParameter("type", type);
        query.setMaxResults(1);
        return (Test) query.getSingleResult();
    }

  
    
    
    
    
}
