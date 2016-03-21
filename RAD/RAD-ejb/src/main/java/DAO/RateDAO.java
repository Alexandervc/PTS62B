package DAO;

import Domain.Rate;
import java.util.List;

/**
 *
 * @author Melanie
 */
public interface RateDAO {
    void create(Rate rate);
    void edit(Rate rate);
    void remove(Rate rate);
    Rate find(Object id);    
    List<Rate> findAll();    
    int count();
}
