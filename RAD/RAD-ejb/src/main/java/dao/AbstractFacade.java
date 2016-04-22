package dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Abstract class for persistends
 * @author Melanie.
 * @param <T> ClassType.
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    private Logger logger;

    /**
     * Constructor AbstractFacade.
     *
     * @param entityClass.
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
        logger = Logger.getLogger(AbstractFacade.class.getName());
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        try {
            return getEntityManager().find(entityClass, id);
        } catch (NoResultException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<T> findAll() {
        try {
            CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            cq.select(cq.from(entityClass));
            return getEntityManager().createQuery(cq).getResultList();
        } catch (NoResultException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int count() {
        try {
            CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            Root<T> rt = cq.from(entityClass);
            cq.select(getEntityManager().getCriteriaBuilder().count(rt));
            Query q = getEntityManager().createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } catch (NoResultException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
