package dao;

import domain.Person;
import java.util.List;

/**
 *
 * @author Linda
 */
public interface PersonDAO {
    void create(Person person);
    void edit(Person person);
    void remove(Person person);
    Person find(Object id);    
    List<Person> findAll();    
    int count();
}
