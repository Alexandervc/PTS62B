/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.clients;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.BillDto;
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
import javax.ws.rs.core.GenericType;
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
            = "http://localhost:8080/RAD-web/radapi";

    // TODO DEPLOY: UNCOMMENT
    //private static final String BASE_URL = 
    //        "http://192.168.24.74:8080/RAD/radapi";
    private Client client;

    @PostConstruct
    private void start() {
        this.client = ClientBuilder.newClient();
    }

    public Long getLoginPerson(String username, String password) {
        LoginUserDto loginUser = new LoginUserDto(username, password);
        List<LoginUserDto> userlist = new ArrayList<>();
        userlist.add(loginUser);
        Gson gson = new Gson();
        String loginJson = gson.toJson(userlist);

        // Get Response
        Response response = this.client.target(BASE_URL)
                .path("/login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(loginJson), Response.class);
        // Check status
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new RuntimeException("Request not accepted: "
                    + response.getStatus());
        }

        // Read entity
        String personJson = response.readEntity(String.class);
        Long l = null;
        try {
            l = Long.parseLong(personJson);
            System.out.println("long l = " + l);
        } catch (NumberFormatException nfe) {
            l = null;
            LOG.log(Level.INFO, null, nfe);
        }

        if (l != null) {
            return l;
        }
        return null;
    }

    public PersonDto getPerson(Long id) {
        Gson gson = new Gson();
        // Get Response
        Response response = this.client.target(BASE_URL)
                .path("/login/{userid}")
                .resolveTemplate("userid", id.toString())
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);

        // Check status
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new RuntimeException("Request not accepted: "
                    + response.getStatus());
        }

        // Read entity
        String personJson = response.readEntity(String.class);
        Type type = new TypeToken<ArrayList<PersonDto>>() {}.getType();
        List<PersonDto> personDto = gson.fromJson(personJson, type);
        if (!personDto.isEmpty()) {
            return personDto.get(0);
        }
        return null;
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
