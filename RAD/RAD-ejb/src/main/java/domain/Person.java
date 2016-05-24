/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * Person class.
 * @author Linda.
 */
@Entity
@NamedQueries({
    @NamedQuery(name="Person.findByName", 
            query="SELECT p FROM Person p WHERE p.firstName = :name"),
    @NamedQuery(name="Person.findPersonsWithText", query = "SELECT p "
            + "FROM Person p "
            + "WHERE UPPER(p.firstName) LIKE UPPER(:searchText) "
            + "OR UPPER(p.lastName) LIKE UPPER(:searchText) "
            + "ORDER BY p.firstName, p.lastName")
})
public class Person implements Serializable {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // Name
    private String firstName;
    private String lastName;
    private String initials;
    
    @Embedded
    private Address address;
    
    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
    private List<Bill> bills;
    
    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST)
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
