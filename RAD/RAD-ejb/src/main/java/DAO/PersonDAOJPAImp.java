/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Domain.Person;
import java.util.List;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Named;

/**
 *
 * @author Linda
 */
@Stateless
@TransactionManagement (TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Named("personRepo")
@Dependent
public class PersonDAOJPAImp implements PersonDAO {

    @PersistenceContext(unitName ="RADpu")
    private EntityManager em;
    
    public PersonDAOJPAImp(){
    }
    
    @Override
    public void setEntityManager(EntityManager em){
        this.em = em;
    }
    
    @Override
    public void create(Person person) {
        em.persist(person);
    }

    @Override
    public List<Person> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Person.class));
        return em.createQuery(cq).getResultList();
    }
    
}
