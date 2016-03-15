/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Domain.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author Linda
 */
public class PersonDAOJPAImp implements PersonDAO {

    private final EntityManager em;
    
    public PersonDAOJPAImp(EntityManager em){
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
