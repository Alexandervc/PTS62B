package service.rest.resources;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The REST resource for cars.
 * @author Melanie
 */
@Path("/persons/{personId}/cars")
@Stateless
public class CarResource {
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getCars(@PathParam("personId") long personId) {
        return null;
    }
}
