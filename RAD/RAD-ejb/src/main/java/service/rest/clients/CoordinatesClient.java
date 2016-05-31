/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.clients;

import dto.Coordinate;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST client for requesting coordinates from RAD.
 * @author Alexander
 */
@Stateless
public class CoordinatesClient {
    private static final String BASE_URL = 
            "http://localhost:8080/VS-web/vsapi";
    
    // TODO DEPLOY: UNCOMMENT
    //private static final String BASE_URL =
    //        "http://192.168.24.72:8080/VS-web/vsapi";
    
    private Client client;
    
    @PostConstruct
    private void start() {
        this.client = ClientBuilder.newClient();
    }
    
    /**
     * Get the coordinates in the given month and year for the given 
     *      cartrackerId.
     * @param month The month to get the coordinates for.
     * @param year The year to get the coordinates for.
     * @param cartrackerId The cartracker to get the coordinates for.
     * @return JSON-string of coordinates.
     */
    public String getCoordinates(String cartrackerId, 
            int month, int year) {
        // Get Response
        Response response = this.client.target(BASE_URL)
                .path("/cartrackers/{cartrackerId}/coordinates")
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
        return response.readEntity(String.class);
    }
}