/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Domain.Bill;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Model;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author Linda
 */
@Stateless
@TransactionManagement (TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Named("billRepo")
@Dependent
public class BillDAOJPAImp implements BillDAO {

    @PersistenceContext(unitName = "RADpu")
    private EntityManager em;

    public BillDAOJPAImp() {
    }

    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public void create(Bill bill) {
        em.persist(bill);
    }

    @Override
    public List<Bill> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Bill.class));
        return em.createQuery(cq).getResultList();
    }
}
