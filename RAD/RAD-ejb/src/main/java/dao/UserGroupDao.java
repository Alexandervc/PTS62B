/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Car;
import domain.UserGroup;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Data access object for UserGroup.
 * @author Alexander
 */
@Stateless
public class UserGroupDao extends AbstractFacade<UserGroup> {
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Data access object for UserGroup.
     */
    public UserGroupDao() {
        super(UserGroup.class);
    }

    /**
     * Getter EntityManager.
     * @return em type EntityManager.
     */
    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
}
