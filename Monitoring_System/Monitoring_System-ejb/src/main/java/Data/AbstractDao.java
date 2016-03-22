/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author Edwin
 * @param <T>
 */
public abstract class AbstractDao<T> {
    private Class<T> entityClass;
    
    @Inject @MonitoringDB EntityManager em;

    public AbstractDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    protected EntityManager getEntityManager() {
        return em;
    };
    
    public void create(T entity) {
        getEntityManager().persist(entity);
    }
    
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }
    
    public void remove(T entity) {
        getEntityManager().remove(entity);
    }
    
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }   
}
