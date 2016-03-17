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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author Linda
 */
public class BillDAOJPAImp implements BillDAO {

    @PersistenceContext(unitName = "RADpu")
    private EntityManager em;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public BillDAOJPAImp() {
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
