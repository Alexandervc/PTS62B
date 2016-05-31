/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.clients;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.rest.contracts.ForeignCountryRide;

/**
 * Helper for storing ForeignCountryRides in RAD via REST.
 * @author Jesse
 */
@Stateless
public class ForeignCountryRideClient {
    private static final String BASE_URL = 
            "http://localhost:8080/RAD-web/radapi";
    
    // TODO DEPLOY: UNCOMMENT
    //private static final String BASE_URL = 
    //        "http://localhost:8080/RAD-web/radapi";
    
    private Client client;

    @PostConstruct
    private void start() {
        this.client = ClientBuilder.newClient();
    }
    
    /**
     * Stores a ForeignCountryRide in the RAD database.
     * @param foreignCountryRideId id for foreign country ride.
     * @param totalPrice total price for ride.
     */
    public void addForeignCountryRide(
            Long foreignCountryRideId, 
            double totalPrice) {
        
        ForeignCountryRide foreignCountryRide = new ForeignCountryRide();
        foreignCountryRide.foreignCountryRideId = foreignCountryRideId; 
        foreignCountryRide.totalPrice = totalPrice;
                
        // Get response
        Response response = this.client.target(BASE_URL)
                .path("/foreignCountryRides")
                .queryParam("foreignCountryRideId", foreignCountryRideId)
                .queryParam("totalPrice", totalPrice)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(
                        foreignCountryRide, 
                        MediaType.APPLICATION_JSON));
        
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            // TODO choose exception
            throw new RuntimeException("Request not accepted: " + 
                    response.getStatus());
        }
    }
}
