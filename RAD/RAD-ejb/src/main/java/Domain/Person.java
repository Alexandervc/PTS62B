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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Linda
 */
@Entity
@NamedQueries({
    @NamedQuery(name="person.findByName", query="SELECT p FROM Person p WHERE p.firstName = :name")
})
public class Person implements Serializable {


    // fields
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Name
    private String firstName;
    private String lastName;
    private String initials;
    
    // Adress
    private String streetName;
    private String number;
    private String zipCode;
    private String city;
    private String country;
    
    // TODO niet verwijzen naar person, aangezien tabel ook zo heet?
    // of person is reserved word??
    @OneToMany(mappedBy = "person2", cascade = CascadeType.PERSIST)
    private List<Bill> bills;
    
    @OneToMany(mappedBy = "person3", cascade = CascadeType.PERSIST)
    private List<Car> cars;
    
    @Deprecated
    public Person() {        
    }

    public Person(String firstname){
        this.firstName = firstname;
        this.bills = new ArrayList<>();
        this.cars = new ArrayList<>();
    }
    public Person(String firstname, String lastname, String initials,
            String streetname, String number, String zipcode, 
            String city, String country) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.initials = initials;
        this.streetName = streetname;
        this.number = number;
        this.zipCode = zipcode;
        this.city = city;
        this.country = country;
        this.bills = new ArrayList<>();
        this.cars = new ArrayList<>();
    }
    
    // getters en setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id){
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public String getInitials() {
        return initials;
    }


    public String getStreetName() {
        return streetName;
    }


    public String getNumber() {
        return number;
    }


    public String getZipCode() {
        return zipCode;
    }


    public String getCity() {
        return city;
    }


    public String getCountry() {
        return country;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    

    public List<Bill> getBills() {
        return bills;
    }
    
    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
    
    /**
     * add bill to list bills
     * @param b 
     */
    public void addBill(Bill b){
        this.bills.add(b);
        b.setPerson2(this);
    }
    
    public void addCar(Car c){
        this.cars.add(c);
        c.setPerson3(this);
    }
}
