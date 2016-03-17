/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Domain.Person;
import java.util.List;

/**
 *
 * @author Linda
 */
public interface PersonDAO {
    void create(Person person);
    
    List<Person> findAll();
}
