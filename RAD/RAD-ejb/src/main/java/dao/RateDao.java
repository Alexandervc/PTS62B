package dao;

import domain.Rate;
import java.util.List;

/**
 * RateDao interface.
 * @author Melanie.
 */
public interface RateDao {
    void create(Rate rate);
    void edit(Rate rate);
    void remove(Rate rate);
    Rate find(Object id);    
    List<Rate> findAll();    
    int count();
}