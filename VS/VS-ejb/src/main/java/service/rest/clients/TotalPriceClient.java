/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.clients;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;
import dto.RoadUsage;

/**
 * Helper for requesting the totalprice from RAD via REST.
 * @author Alexander
 */
@Stateless
public class TotalPriceClient {
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
     * Get the total price for the given roadUsages.
     *
     * @param roadUsages The given roadUsages to get the price for.
     * @return List of RoadUsage.
     */
    public Double getTotalPrice(List<RoadUsage> roadUsages) {
        // TODO cannot send List
        Gson gson = new Gson();
        String roadUsagesJson = gson.toJson(roadUsages);
        Response response = this.client.target(BASE_URL)
                .path("totalPrice")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(roadUsagesJson), Response.class);

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            // TODO choose exception
            throw new RuntimeException("Request not accepted: " + 
                    response.getStatus());
        }

        return response.readEntity(Double.class);
    }
}
