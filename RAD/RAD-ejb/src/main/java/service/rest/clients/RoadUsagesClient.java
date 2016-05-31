/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.clients;

import dto.BillRoadUsage;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Helper for requesting roadUsages from VS via REST.
 *
 * @author Alexander
 */
@Stateless
public class RoadUsagesClient {

    private static final String BASE_URL
            = "http://localhost:8080/VS-web/vsapi";

    // TODO DEPLOY: UNCOMMENT
    //private static final String BASE_URL =
    //        "http://localhost:8080/VS-web/vsapi";
    private Client client;

    @PostConstruct
    private void start() {
        this.client = ClientBuilder.newClient();
    }

    /**
     * Get the roadUsages for the given cartrackerId between the given beginDate
     * and endDate.
     *
     * @param cartrackerId The cartracker id to get the roadUsages for.
     * @param month The month to get the roadUsages for.
     * @param year The year to get the roadUsages for.
     * @return List of BillRoadUsage.
     */
    public List<BillRoadUsage> getRoadUsages(String cartrackerId, int month,
            int year) {
        // Get response
        Response response = this.client.target(BASE_URL)
                .path("/cartrackers/{cartrackerId}/roadUsages")
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
        GenericType<List<BillRoadUsage>> roadUsageType
                = new GenericType<List<BillRoadUsage>>() {};
        return response.readEntity(roadUsageType);
    }
}
