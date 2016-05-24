/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.resources;

import dto.Coordinate;
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
import service.CarPositionService;

/**
 * The REST resource for coordinates.
 * @author Alexander
 */
@Path("/cartrackers/{cartrackerId}/coordinates")
@Stateless
public class CoordinatesResource {
    @Inject
    private CarPositionService carPositionService;
    
    /**
     * Get the coordinates in the given month and year for the given 
     *      cartrackerId.
     * @param month The month to get the coordinates for.
     * @param year The year to get the coordinates for.
     * @param cartrackerId The cartracker to get the coordinates for.
     * @return A response containing a list of coordinates.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCoordinates(
            @PathParam("cartrackerId") String cartrackerId,
            @QueryParam("month") int month, 
            @QueryParam("year") int year) {
        
        // Get coordinates
        List<Coordinate> coordinates = this.carPositionService
                .getCoordinates(month, year, cartrackerId);
        
        // Make response
        return Response.status(Response.Status.OK)
                .entity(coordinates)
                .build();
    }
}
