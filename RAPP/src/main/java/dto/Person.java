/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Person class.
 * @author Linda.
 */
public class Person implements Serializable {
    private Long id;
    
    // Name
    private String firstName;
    private String lastName;
    private String initials;
    
    private Address address;
    
    private List<Bill> bills;
    
    private List<Car> cars;
    
    /**
     * Empty Contructor.
     * 
     * @deprecated contructor for JPA.
     */
    @Deprecated
    public Person() { 
        // Empty for JPA.
    }

    /**
     * Contructor with one parameter.
     * @param firstname of person.
     */
    public Person(String firstname){
        this.firstName = firstname;
        this.bills = new ArrayList<>();
        this.cars = new ArrayList<>();
    }
    
    /**
     * Constructor conplete.
     * @param firstname of person.
     * @param lastname of person.
     * @param initials of person.
     * @param address of person.
     */
    public Person(String firstname, String lastname, String initials,
            Address address) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.initials = initials;
        this.address = address;
        this.bills = new ArrayList<>();
        this.cars = new ArrayList<>();
    }
    
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id){
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getInitials() {
        return this.initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Bill> getBills() {
        return new ArrayList<>(this.bills);
    }
    
    public void setBills(List<Bill> bills) {
        this.bills = new ArrayList<>(bills);
    }

    public List<Car> getCars() {
        return new ArrayList<>(this.cars);
    }

    public void setCars(List<Car> cars) {
        this.cars = new ArrayList<>(cars);
    }
    
    /**
     * Add bill to list bills.
     * @param b bill.
     */
    public void addBill(Bill b){
        this.bills.add(b);
        b.setPerson(this);
    }
    
    /**
     * Add car to list cars.
     * @param c car.
     */
    public void addCar(Car c){
        this.cars.add(c);
        c.setOwner(this);
    }
}
