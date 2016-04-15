/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import javax.persistence.EntityManager;

/**
 * The facade with basic functionality for dao's.
 * @author Alexander
 */
public abstract class AbstractDaoFacade<T> implements Serializable {
    private Class<T> entityClass;

    public AbstractDaoFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        this.getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        this.getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        this.getEntityManager().remove(this.getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return this.getEntityManager().find(this.entityClass, id);
    }
}
