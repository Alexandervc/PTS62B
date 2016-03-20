/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Domain.Bill;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import DAO.IBillDAO;

/**
 *
 * @author Linda
 */
@Stateless
public class BillDAOJPAImp implements IBillDAO  {

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