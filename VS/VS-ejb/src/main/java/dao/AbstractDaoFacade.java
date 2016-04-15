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
 * @param <T> The entity class.
 */
public abstract class AbstractDaoFacade<T> implements Serializable {
    private Class<T> entityClass;

    /**
     * The facade with basic functionality for dao's.
     * @param entityClass The entity class.
     */
    public AbstractDaoFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    protected abstract EntityManager getEntityManager();

    /**
     * Save the given entity to the database.
     * @param entity The entity to save.
     */
    public void create(T entity) {
        this.getEntityManager().persist(entity);
    }

    /**
     * Edit the given entity in the database.
     * @param entity The entity to edit.
     */
    public void edit(T entity) {
        this.getEntityManager().merge(entity);
    }

    /**
     * Remove the given entity from the database.
     * @param entity The entity to remove.
     */
    public void remove(T entity) {
        this.getEntityManager().remove(this.getEntityManager().merge(entity));
    }

    /**
     * Find the given entity in the database.
     * @param id The id of the entity to find.
     * @return The found entity or null.
     */
    public T find(Object id) {
        return this.getEntityManager().find(this.entityClass, id);
    }
}
