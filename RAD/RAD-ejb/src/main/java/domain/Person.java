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
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * Person class.
 *
 * @author Linda.
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Person.findByName",
            query = "SELECT p FROM Person p WHERE p.firstName = :name"),
    @NamedQuery(name = "Person.findPersonsWithText", query = "SELECT p "
            + "FROM Person p "
            + "WHERE UPPER(p.firstName) LIKE UPPER(:searchText) "
            + "OR UPPER(p.lastName) LIKE UPPER(:searchText) "
            + "ORDER BY p.firstName, p.lastName"),
    @NamedQuery(name = "Person.findByCartrackerId", query = "SELECT p "
            + "FROM Person p, Car c "
            + "WHERE p.id = c.owner.id "
            + "AND c.cartrackerId = :cartrackerId"),
    @NamedQuery(name = "Person.findByUsername",
            query = "SELECT p FROM Person p WHERE p.username = :username ")
})
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Name
    private String firstName;
    private String lastName;
    private String initials;

    //login
    @Column(name = "USERNAME", unique = true)
    private String username;
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
    private List<Bill> bills;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST)
    private List<Car> cars;
    
    @ManyToMany (cascade = CascadeType.ALL)
    @JoinTable(name="person_usergroup",
        joinColumns = @JoinColumn(name = "person_username", 
                            referencedColumnName = "username"), 
        inverseJoinColumns = @JoinColumn(name = "usergroup_groupname", 
                            referencedColumnName = "groupname"))
    private List<UserGroup> groups;

    /**
     * Empty Contructor.
     *
     * @deprecated contructor for JPA.
     */
    @Deprecated
    public Person() {
        // Empty for JPA.
        this.groups = new ArrayList<>();
    }

    /**
     * Contructor with one parameter.
     *
     * @param firstname of person.
     */
    public Person(String firstname) {
        this.firstName = firstname;
        this.bills = new ArrayList<>();
        this.cars = new ArrayList<>();
        this.groups = new ArrayList<>();
    }

    /**
     * Constructor conplete.
     *
     * @param firstname of person.
     * @param lastname of person.
     * @param initials of person.
     * @param username of person.
     * @param password Hashed password of person.
     * @param address of person.
     */
    public Person(String firstname, String lastname, String initials,
            String username, String password, Address address) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.initials = initials;
        this.username = username;
        this.password = password;
        this.address = address;
        this.bills = new ArrayList<>();
        this.cars = new ArrayList<>();
        this.groups = new ArrayList<>();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
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

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
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
     *
     * @param b bill.
     */
    public void addBill(Bill b) {
        this.bills.add(b);
        b.setPerson(this);
    }

    /**
     * Add car to list cars.
     *
     * @param c car.
     */
    public void addCar(Car c) {
        this.cars.add(c);
        c.setOwner(this);
    }
    
    /**
     * Add group for security roles.
     * @param group The group to add. Cannot be null. Cannot be already added.
     */
    public void addGroup(UserGroup group) {
        if(group == null) {
            throw new IllegalArgumentException("Group null");
        }
        if(this.groups.contains(group)) {
            throw new IllegalArgumentException("Group already added");
        }
        this.groups.add(group);
    }
}
