/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.clients;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.BillDto;
import dto.CarDto;
import dto.LoginUserDto;
import dto.PersonDto;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Jesse
 */
@Stateless
public class BillClient {

    private static final Logger LOG = Logger.
            getLogger(BillClient.class.getName());
    private static final String BASE_URL
            = "http://192.168.24.74:8080/RAD/radapi";

    // TODO DEPLOY: UNCOMMENT
    //private static final String BASE_URL = 
    //        "http://192.168.24.74:8080/RAD/radapi";
    private Client client;

    @PostConstruct
    private void start() {
        this.client = ClientBuilder.newClient();
    }

    /**
     * Get the personid with correct username and password, otherwise null.
     *
     * @param username of login.
     * @param password of login.
     * @return object of personDto.
     */
    public PersonDto getLoginPerson(String username, String password) {
        LoginUserDto loginUser = new LoginUserDto(username, password);
        Gson gson = new Gson();
        String loginJson = gson.toJson(loginUser);

        // Get Response
        Response response = this.client.target(BASE_URL)
                .path("/login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(loginJson), Response.class);
        // Check status
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
                LOG.log(Level.SEVERE, "Request not accepted: "
                        + response.getStatus());
                return null;

            } else {
                throw new RuntimeException("Request not accepted: "
                        + response.getStatus());
            }
        }

        // Read entity
        String personJson = response.readEntity(String.class);
        PersonDto personDto = gson.fromJson(personJson, PersonDto.class);
        if (personDto != null) {
            return personDto;
        }
        return null;
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

        // Read entity
        Gson gson = new Gson();
        String carsJson = response.readEntity(String.class);
        Type type = new TypeToken<ArrayList<CarDto>>() { }.getType();
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
        // Get Response
        Response response = this.client.target(BASE_URL)
                .path("/cartrackers/{cartrackerId}/bill")
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

        Gson gson = new Gson();
        String billJson = response.readEntity(String.class);
        BillDto billDto = gson.fromJson(billJson, BillDto.class);
        return billDto;
    }
}
