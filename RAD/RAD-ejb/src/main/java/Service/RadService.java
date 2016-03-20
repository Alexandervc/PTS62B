/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Domain.Bill;
import Domain.Person;
import Domain.RoadType;
import Domain.RoadUsage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import DAO.IBillDAO;
import DAO.IPersonDAO;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Linda
 */
@Stateless
public class RadService extends UnicastRemoteObject implements IRadService {

    @Inject
    private IBillDAO billDAO;
    @Inject
    private IPersonDAO personDAO;

    public RadService() throws RemoteException {

    }

//    @Override
//    public void test() {
////        Bill b = new Bill();
////        RoadUsage road = new RoadUsage(1L, "AutoWeg", RoadType.A, 25.36);
////        List<RoadUsage> roads = new ArrayList<>();
////        roads.add(road);
////        b.setRoadUsage(roads);
////        persistBill(b);
////
////        Person p = new Person();
////        p.setName("Test");
////        p.setCartracker(9L);
////        p.addBill(b);
////        persistPerson(p);
//    }

//    @Override
//    public void persistBill(Bill b) {
//        try {
//            billDAO.create(b);
//        } catch (Exception e) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public List<Bill> findAllBill() {
//        try {
//            List<Bill> bills = billDAO.findAll();
//            return bills;
//        } catch (Exception e) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public void persistPerson(Person p) {
//        try {
//            personDAO.create(p);
//        } catch (Exception e) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public List<Person> findAllPerson() {
//        try {
//            List<Person> persons = personDAO.findAll();
//            return persons;
//        } catch (Exception e) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
//            throw new RuntimeException(e);
//        }
//    }
}
