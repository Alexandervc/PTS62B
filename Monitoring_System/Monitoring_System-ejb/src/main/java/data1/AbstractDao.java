/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data1;

import javax.persistence.EntityManager;

/**
 * An abstract Dao that can be implemented by other Dao's to give 
 * basic functionality to the Dao.
 * @author Edwin.
 * @param <T> The entity class the Dao has to work with.
 */
public abstract class AbstractDao<T> {
    private final Class<T> entityClass;
    
    /**
     * Constructor that can be used when creating a Dao that inherits
     * this abstract Dao.
     * @param entityClass The entityClass that the Dao has to work with.
     */
    public AbstractDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    protected abstract EntityManager getEntityManager();

    /**
     * Creates the an object in the database using JPA.
     * @param entity The object that has to be created.
     */
    public void create(T entity) {
        this.getEntityManager().persist(entity);
    }
    
    /**
     * Updates the object in the database using JPA.
     * @param entity The object that has to be updated.
     */
    public void edit(T entity) {
        this.getEntityManager().merge(entity);
    }
    
    /**
     * Removes an object from the database using JPA.
     * @param entity The object that has to be removed.
     */
    public void remove(T entity) {
        this.getEntityManager().remove(entity);
    }
    
    /**
     * Finds an object with a given id.
     * @param id The unique identifier of the object that has to be found.
     * @return The object with the unique identifier.
     */
    public T find(Object id) {
        return this.getEntityManager().find(this.entityClass, id);
    }   
}
