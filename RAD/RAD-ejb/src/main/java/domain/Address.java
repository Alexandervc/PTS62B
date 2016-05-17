/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * Represents an address.
 * @author Alexander
 */
@Embeddable
public class Address implements Serializable {
    // Adress
    private String streetname;
    private String housenumber;
    private String zipcode;
    private String city;
    
    /**
     * Empty constructor.
     * @deprecated For JPA.
     */
    @Deprecated
    public Address() {
        // For JPA
    }
    
    /**
     * Represents an address.
     * @param streetname The name of the street.
     * @param housenumber The housenumber.
     * @param zipcode The zipcode.
     * @param city The city.
     */
    public Address(String streetname, String housenumber, String zipcode,
            String city) {
        this.streetname = streetname;
        this.housenumber = housenumber;
        this.zipcode = zipcode;
        this.city = city;
    }

    public String getStreetname() {
        return this.streetname;
    }

    public void setStreetname(String streetname) {
        this.streetname = streetname;
    }

    public String getHousenumber() {
        return this.housenumber;
    }

    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    public String toString() {
        return String.format("{0} {1}, {2} {3}", this.streetname,
                this.housenumber, this.zipcode, this.city);
    }
}
