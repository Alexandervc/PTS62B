/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.clients;

import dto.BillDto;
import dto.PersonDto;
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
    
    public PersonDto getPerson(String username, String password){
        // Get Response
        Response response = this.client.target(BASE_URL)
                .path("/inlog/{username}/{password}/person")
                .resolveTemplate("username", username)
                .resolveTemplate("password", password)
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);
        
        // Check status
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new RuntimeException("Request not accepted: "
                    + response.getStatus());
        }
        
        // Read entity
        GenericType<PersonDto> personType = new GenericType<PersonDto>() {};
        return response.readEntity(personType);
    }
    
    /**
     * Get the bill for a cartracker.
     * @param cartrackerId The id of the cartracker to generate the bill for.
     * @param month The number of the month to generate the bill for.
     * @param year The number of the year to generate the bill for.
     * @return A bill for the cartrackerId.
     */
    public BillDto getBill(String cartrackerId, int month, int year) {
        // Get Response
        Response response = this.client.target(BASE_URL)
                .path("/cartracker/{cartrackerId}/bill")
                .resolveTemplate("cartrackerId", cartrackerId)
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
        return response.readEntity(BillDto.class);
    }
}
