package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Abstract class for persistends.
 * @author Melanie.
 * @param <T> ClassType.
 */
public abstract class AbstractFacade<T> {
    private static final Logger LOGGER = Logger
            .getLogger(AbstractFacade.class.getName());
    
    private Class<T> entityClass;    

    /**
     * Constructor AbstractFacade.
     *
     * @param entityClass Object.
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    /**
     * Create object in db.
     * @param entity object T.
     */
    public void create(T entity) {
        this.getEntityManager().persist(entity);
    }

    /**
     * Edit object in db.
     * @param entity object T.
     */
    public void edit(T entity) {
        this.getEntityManager().merge(entity);
    }

    /**
     * Remove object in db.
     * @param entity object T.
     */
    public void remove(T entity) {
        this.getEntityManager().remove(this.getEntityManager().merge(entity));
    }

    /**
     * Find object in db.
     * @param id of object.
     * @return object T.
     */
    public T find(Object id) {
        try {
            return this.getEntityManager().find(this.entityClass, id);
        } catch (NoResultException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Find all objects T.
     * @return List object T.
     */
    public List<T> findAll() {
        List<T> objects = new ArrayList<>();
        try {
            CriteriaQuery cq = this.getEntityManager()
                    .getCriteriaBuilder().createQuery();
            cq.select(cq.from(this.entityClass));
            objects = this.getEntityManager().createQuery(cq).getResultList();
        } catch (NoResultException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return objects;
    }

    /**
     * count object in db.
     * @return integer count.
     */
    public int count() {
        try {
            CriteriaQuery cq = this.getEntityManager()
                    .getCriteriaBuilder().createQuery();
            Root<T> rt = cq.from(this.entityClass);
            cq.select(this.getEntityManager().getCriteriaBuilder().count(rt));
            Query q = this.getEntityManager().createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } catch (NoResultException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
}
