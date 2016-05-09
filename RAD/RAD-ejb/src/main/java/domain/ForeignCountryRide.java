/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Provides the functionality regarding the ForeignCountryRide entity.
 * @author Jesse
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "foreignCountryRide.findByForeignCountryRideId",
            query = "SELECT f "
                    + "FROM ForeignCountryRide f "
                    + "WHERE f.foreignCountryRideId = :foreignCountryRideId")
})
public class ForeignCountryRide implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /**
     * Id of the foreign country ride, this is also stored in a the RoadUsage
     * object.
     */
    private Long foreignCountryRideId;
    
    /**
     * Total price of the foreign country ride.
     */
    private double totalPrice;
    
    /**
     * Empty constructor for JPA.
     */
    public ForeignCountryRide() {
        // Empty constructor for JPA.
    }
    
    /**
     * Instantiates the ForeignCountryRide class.
     * @param foreignCountryRideId The id of the foreign country ride.
     * @param totalPrice The total price of the foreign country ride.
     */
    public ForeignCountryRide(Long foreignCountryRideId, double totalPrice) {
        this.foreignCountryRideId = foreignCountryRideId;
        this.totalPrice = totalPrice;
    }

    /**
     * Gets the Id.
     * @return The Id.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Sets the Id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the ForeignCountryRideId.
     * @return The ForeignCountryRideId.
     */
    public Long getForeignCountryRideId() {
        return this.foreignCountryRideId;
    }

    /**
     * Gets the ForeignCountryRideId.
     */
    public void setForeignCountryRideId(Long foreignCountryRideId) {
        this.foreignCountryRideId = foreignCountryRideId;
    }

    /**
     * Gets the total price.
     * @return The total price.
     */
    public double getTotalPrice() {
        return this.totalPrice;
    }

    /**
     * Sets the total price.
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }   
}