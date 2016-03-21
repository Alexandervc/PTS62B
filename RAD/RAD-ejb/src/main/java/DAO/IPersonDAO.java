/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Domain.Person;
import java.rmi.Remote;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Linda
 */
public interface IPersonDAO {
    void setEntityManager(EntityManager em);
    
    void create(Person person);
    
    List<Person> findAll();
}
