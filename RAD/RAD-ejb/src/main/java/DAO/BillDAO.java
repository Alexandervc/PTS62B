/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Domain.Bill;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Linda
 */
public interface BillDAO {
    
    void create(Bill bill);
    
    List<Bill> findAll();  
}
