/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.clients;

import dto.Bill;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Jesse
 */
@Stateless
public class BillClient {
    private static final String BASE_URL = 
            "http://localhost:8080/RAD-web/radapi";
    
    // TODO DEPLOY: UNCOMMENT
    //private static final String BASE_URL = 
    //        "http://192.168.24.74:8080/RAD/radapi";
    
    private Client client;
    
    @PostConstruct
    private void start() {
        this.client = ClientBuilder.newClient();
    }
    
    /**
     * Get the bill which is generated for each car of the given person.
     * @param userId The id of the user to generate the bill for.
     * @param month The number of the month to generate the bill for.
     * @param year The number of the year to generate the bill for.
     * @return A list of Bills, one Bill for each car of the person.
     */
    public List<Bill> getBill(long userId, int month, int year) {
        // Get Response
        Response response = this.client.target(BASE_URL)
                .path("/bills/{userId}")
                .resolveTemplate("userId", userId)
                .queryParam("month", month)
                .queryParam("year", year)
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);
        
        // Check status
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new RuntimeException("Request not accepted: "
                    + response.getStatus());
        }
        
        // Read entity
        GenericType<List<Bill>> billType = new GenericType<List<Bill>>() {};
        return response.readEntity(billType);
    }
}
