package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Linda
 */
@Entity (name = "Person")
public class Person implements Serializable {
    
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
    
    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
    private List<Bill> bills;
    
    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
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

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
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
        b.setPerson(this);
    }
    
    public void addCar(Car c){
        this.cars.add(c);
        c.setPerson(this);
    }
}
