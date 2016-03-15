/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Domain.Bill;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author Linda
 */
public class BillDAOJPAImp implements BillDAO {

    private final EntityManager em;
    
    public BillDAOJPAImp(EntityManager em){
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
        return em.createQuery(cq).getResultList();    }
    
}
