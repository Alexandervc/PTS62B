/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Domain.Person;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import DAO.IPersonDAO;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Linda
 */
@Stateless
public class PersonDAOJPAImp extends UnicastRemoteObject implements IPersonDAO {

    @PersistenceContext(unitName ="RADpu")
    private EntityManager em;
    
    public PersonDAOJPAImp() throws RemoteException{
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
