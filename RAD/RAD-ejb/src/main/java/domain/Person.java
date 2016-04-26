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
 * Person class.
 * @author Linda.
 */
@Entity
@NamedQueries({
    @NamedQuery(name="person.findByName", 
            query="SELECT p FROM Person p WHERE p.firstName = :name")
})
public class Person implements Serializable {

    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // Name
    private String firstName;
    private String lastName;
    private String initials;
    
    // Adress
    private String streetName;
    private String housenumber;
    private String zipCode;
    private String city;
    private String country;
    
    @OneToMany(mappedBy = "person2", cascade = CascadeType.PERSIST)
    private List<Bill> bills;
    
    @OneToMany(mappedBy = "person3", cascade = CascadeType.PERSIST)
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
     * @param streetname of person.
     * @param housenumber of person.
     * @param zipcode of person.
     * @param city of person.
     * @param country of person.
     */
    public Person(String firstname, String lastname, String initials,
            String streetname, String housenumber, String zipcode, 
            String city, String country) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.initials = initials;
        this.streetName = streetname;
        this.housenumber = housenumber;
        this.zipCode = zipcode;
        this.city = city;
        this.country = country;
        this.bills = new ArrayList<>();
        this.cars = new ArrayList<>();
    }
    
    /**
     * Getter Id.
     * @return Id.
     */
    public Long getId() {
        return this.id;
    }
    /**
     * Setter Id.
     * @param id of person. 
     */
    public void setId(Long id){
        this.id = id;
    }

    /**
     * Getter First name.
     * @return String first name.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Setter First name.
     * @param firstName of person. 
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter last name.
     * @return String Last name.
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Setter Last name.
     * @param lastName of person.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * Getter Initials.
     * @return String initials.
     */
    public String getInitials() {
        return this.initials;
    }

    /**
     * Setter Initials.
     * @param initials of person.
     */
    public void setInitials(String initials) {
        this.initials = initials;
    }

    /**
     * Getter Streetname.
     * @return String streetname.
     */
    public String getStreetName() {
        return this.streetName;
    }
    /**
     * Setter Streetname.
     * @param streetName of person. 
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * Getter Housenumber.
     * @return String Housenumber.
     */
    public String getHousenumber() {
        return this.housenumber;
    }
/**
 * Setter Housenumber.
 * @param housenumber of person. 
 */
    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
    }

    /**
     * Getter Zipcode.
     * @return String zipcode.
     */
    public String getZipCode() {
        return this.zipCode;
    }

    /**
     * Setter Zipcode.
     * @param zipCode of person. 
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    /**
     * Getter City.
     * @return String City.
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Setter City.
     * @param city of person. 
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Getter Country.
     * @return String Country.
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * Setter Country.
     * @param country of person. 
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Getter bills.
     * @return list bills.
     */
    public List<Bill> getBills() {
        return new ArrayList<>(this.bills);
    }
    
    /**
     * Setter bills.
     * @param bills of person.
     */
    public void setBills(List<Bill> bills) {
        this.bills = new ArrayList<>(bills);
    }

    /**
     * Getter cars.
     * @return List cars.
     */
    public List<Car> getCars() {
        return new ArrayList<>(this.cars);
    }

    /**
     * Setter cars.
     * @param cars of person. 
     */
    public void setCars(List<Car> cars) {
        // TODO!!
        this.cars = new ArrayList<Car>(this.cars);
    }
    
    /**
     * Add bill to list bills.
     * @param b bill.
     */
    public void addBill(Bill b){
        this.bills.add(b);
        b.setPerson2(this);
    }
    
    /**
     * Add car to list cars.
     * @param c car.
     */
    public void addCar(Car c){
        this.cars.add(c);
        c.setPerson3(this);
    }
    
}
