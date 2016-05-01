/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.resources;

import dto.RoadUsage;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Alexander
 */
@Path("/cartrackers/{cartrackerId}/roadUsages")
@Stateless
public class RoadUsagesResource {
    private static final Logger LOGGER =
            Logger.getLogger(RoadUsagesResource.class.getName());
    
    @Inject
    private RoadUsageService roadUsageService;

    /**
     * Get the roadUsages for the given cartrackerId between the given beginDate
     *      and endDate.
     * @param cartrackerId The cartracker id to get the roadUsages for.
     * @param beginDateString The begin of the period to get the roadUsages 
     *      in between. In format dd-MM-yyyy HH:mm:ss.
     * @param endDateString The end of the period to get the roadUsages in
     *      between. In format dd-MM-yyyy HH:mm:ss.
     * @return If successfull response with status OK and as entity the 
     *      roadUsages as a List of RoadUsage. If one of the dates is in the 
     *      wrong format a response with status BAD_REQUEST will be returned.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoadUsagesBetween(
            @PathParam("cartrackerId") String cartrackerId,
            @QueryParam("beginDate") String beginDateString,
            @QueryParam("endDate") String endDateString) {
        try {
            // Convert dates
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date beginDate = df.parse(beginDateString);
            Date endDate = df.parse(endDateString);
            
            // Generate road usages
            List<RoadUsage> roadUsages = this.roadUsageService
                    .generateRoadUsages(cartrackerId, beginDate, endDate);
            
            // Make response
            return Response.status(Response.Status.OK)
                    .entity(roadUsages)
                    .build();
        } catch (ParseException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }
}
