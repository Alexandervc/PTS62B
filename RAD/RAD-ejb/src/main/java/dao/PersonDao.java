package dao;

import domain.Person;
import java.util.List;

/**
 * PersonDao interface.
 * @author Linda.
 */
public interface PersonDao {
    void create(Person person);
    void edit(Person person);
    void remove(Person person);
    Person find(Object id);    
    List<Person> findAll();    
    int count();
    
    Person findByName(String name);
}