/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.clients;

import com.google.gson.reflect.TypeToken;
import dto.BillDto;
import dto.CarDto;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Provides functionality regarding the Bill.
 * @author Jesse
 */
@Stateless
public class BillClient extends BaseClient {

    private static final Logger LOG = Logger.
            getLogger(BillClient.class.getName());
    private static final String BASE_URL
            = "http://localhost:8080/RAD-web/radapi";
    
    private Client client;

    /**
     * Instantiates the BillClient.
     */
    public BillClient() {
        super();
    }
    
    @PostConstruct
    private void start() {
        this.client = ClientBuilder.newClient();
    }

    /**
     * Get list of cars for person.
     * @param restLink link for restapi.
     * @return List of CarDto objects.
     */
    public List<CarDto> getCars(String restLink) {
        // Get Response
        Response response = this.client.target(BASE_URL)
                .path(restLink)
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);

        // Check status
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new RuntimeException("Request not accepted: "
                    + response.getStatus());
        }
        
        // Decrypt message.
        String encryptedJson = response.readEntity(String.class);
        String carsJson = this.decrypt(encryptedJson);
        Type type = new TypeToken<ArrayList<CarDto>>() { }.getType();
        
        // Convert decrypted JSON to List<CarDto>.
        List<CarDto> carsDto = gson.fromJson(carsJson, type);
        return carsDto;
    }

    /**
     * Get the bill for a cartracker.
     *
     * @param cartrackerId The id of the cartracker to generate the bill for.
     * @param month The number of the month to generate the bill for.
     * @param year The number of the year to generate the bill for.
     * @return A bill for the cartrackerId.
     */
    public BillDto getBill(String cartrackerId, int month, int year) {
        // Get Response.
        Response response = this.client.target(BASE_URL)
                .path("/cartrackers/{cartrackerId}/bill")
                .resolveTemplate("cartrackerId", cartrackerId)
                .queryParam("month", month)
                .queryParam("year", year)
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);

        // Check status.
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new RuntimeException("Request not accepted: "
                    + response.getStatus());
        }

        // Decrypt message.
        String encryptedJson = response.readEntity(String.class);
        String billJson = this.decrypt(encryptedJson);

        // Convert decrypted JSON to BillDto.
        BillDto billDto = this.gson.fromJson(billJson, BillDto.class);
        return billDto;
    }
}
