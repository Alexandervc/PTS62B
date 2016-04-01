/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Common.Domain.Test;
import Common.Domain.System;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Edwin
 */
@Stateless
public class SystemDao extends AbstractDao {

    @Inject @MonitoringDB 
    private EntityManager em;

    public SystemDao() {
        super(Test.class);
    }

    public List<System> getSystems() {
        Query query = this.em.createNamedQuery("get systems");
        return query.getResultList();
    }
    
    

  
    
    
    
    
}
