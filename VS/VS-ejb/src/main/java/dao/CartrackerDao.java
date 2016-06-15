/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Cartracker;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * The dao for cartracker.
 * @author Alexander
 */
@Stateless
public class CartrackerDao extends AbstractDaoFacade<Cartracker> {
    @PersistenceContext
    private EntityManager em;
    
    /**
     * The dao for cartracker.
     */
    public CartrackerDao() {
        super(Cartracker.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
}
