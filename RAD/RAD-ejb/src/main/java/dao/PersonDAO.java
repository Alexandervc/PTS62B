package dao;

import domain.Person;
import java.util.List;

/**
 * PersonDAO interface
 * @author Linda
 */
public interface PersonDAO {
    void create(Person person);
    void edit(Person person);
    void remove(Person person);
    Person find(Object id);    
    List<Person> findAll();    
    int count();
    
    Person findByName(String name);
}