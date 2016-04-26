/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Car;
import java.util.List;

/**
 * CarDao interface.
 * @author Linda.
 */
public interface CarDao {
    void create(Car car);
    void edit(Car car);
    void remove(Car car);
    Car find(Object id);    
    List<Car> findAll();    
    int count();
}
