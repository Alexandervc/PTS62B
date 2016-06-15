/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.resources;

import dto.RoadUsage;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.RoadUsageService;

/**
 * The REST resource for RoadUsages.
 *
 * @author Alexander
 */
@Path("/cartrackers/{cartrackerId}/roadUsages")
@Stateless
public class RoadUsagesResource {
    @Inject
    private RoadUsageService roadUsageService;

    /**
     * Get the roadUsages for the given cartrackerId and the given month.
     *
     * @param cartrackerId The cartracker id to get the roadUsages for.
     * @param month The month to get the roadUsages for.
     * @param year The year to get the roadUsages for.
     * @return If successfull response with status OK and as entity the
     *      roadUsages as a List of RoadUsage. If one of the dates is in the wrong
     *      format a response with status BAD_REQUEST will be returned.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoadUsagesBetween(
            @PathParam("cartrackerId") String cartrackerId,
            @QueryParam("month") int month,
            @QueryParam("year") int year) {

        // Generate road usages
        List<RoadUsage> roadUsages = this.roadUsageService
                .generateRoadUsages(cartrackerId, month, year);

        // Make response
        return Response.status(Response.Status.OK)
                .entity(roadUsages)
                .build();
    }
}
