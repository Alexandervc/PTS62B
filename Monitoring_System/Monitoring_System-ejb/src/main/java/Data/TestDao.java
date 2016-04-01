/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import common.domain.Test;
import common.domain.TestType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Edwin
 */
@Stateless
public class TestDao extends AbstractDao {

    @Inject
    private @MonitoringDB EntityManager em;

    public TestDao() {
        super(Test.class);
    }
    
    public Test retrieveLatestTestForTypeForSystem(common.domain.System system
            , TestType type) {
        Query query = this.em.createNamedQuery("get latest test for system with type");
        query.setParameter("systemId", system.getId());
        query.setParameter("type", type);
        query.setMaxResults(1);
        return (Test) query.getSingleResult();
    }

  
    
    
    
    
}
