package service.rest.resources;

import domain.Car;
import domain.Person;
import dto.CarDto;
import static dto.DtoConverter.convertCarsToDto;
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
import service.PersonService;

/**
 * The REST resource for cars.
 * @author Melanie
 */
@Path("/persons/{personId}/cars")
@Stateless
public class CarResource extends BaseResource {
    @Inject
    private PersonService personService;

    /**
     * Instantiates the CarResource class. This also reads all the keys needed
     * for encryption and decryption.
     */
    public CarResource() {
        super();
    }
    
    /**
     * Get cars for person.
     * @param personId id for person.
     * @param key The api key.
     * @return Response, with list of cars.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCars(@PathParam("personId") long personId,
                            @QueryParam("key") String key) {
        // Check api key.
        if (!this.radApiKey.equals(key)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                           .build();
        }
        
        Person person = this.personService.findPersonById(personId);
        List<Car> cars = person.getCars();
        
        List<CarDto> dtos = convertCarsToDto(cars);
        String encrypted = this.encrypt(this.gson.toJson(dtos));

        if (encrypted != null) {
            return Response.status(Response.Status.OK)
                           .entity(encrypted)
                           .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                           .build();
        }
    }
}
