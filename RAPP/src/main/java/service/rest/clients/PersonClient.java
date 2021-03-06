/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.clients;

import dto.PersonDto;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST client for requesting persons from RAD.
 *
 * @author Alexander
 */
@Stateless
public class PersonClient extends BaseClient {
    private static final String BASE_URL
            = "http://192.168.24.74:8080/RAD/radapi";

    private Client client;

    /**
     * Instantiates the PersonClient class.
     */
    public PersonClient() {
        super();
    }

    @PostConstruct
    private void start() {
        this.client = ClientBuilder.newClient();
    }

    /**
     * Get the person with the given username
     *
     * @param username The username of the person.
     * @return The found person.
     */
    public PersonDto getPerson(String username) {
        // Get Response
        Response response = this.client.target(BASE_URL)
                .path("/persons/{username}")
                .resolveTemplate("username", username)
                .queryParam("key", this.radApiKey)
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);

        // Check status
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new RuntimeException("Request not accepted: "
                    + response.getStatus());
        }
        
        // Decrypt message.
        String encryptedJson = response.readEntity(String.class);
        String personsJson = this.decrypt(encryptedJson);
        
        // Convert decrypted JSON to List<CarDto>.
        PersonDto personDto = gson.fromJson(personsJson, PersonDto.class);
        return personDto;
    }
}
