package service.rest.resources;

import com.google.gson.Gson;
import domain.Car;
import domain.Person;
import dto.CarDto;
import static dto.DtoConverter.convertCarsToDto;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.PersonService;

/**
 * The REST resource for cars.
 * @author Melanie
 */
@Path("/persons/{personId}/cars")
@Stateless
public class CarResource {
    @Inject
    private PersonService personService;
    
    /**
     * Get cars for person.
     * @param personId id for person.
     * @return Response, with list of cars.
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getCars(@PathParam("personId") long personId) {
        Person person = personService.findPersonById(personId);
        List<Car> cars = person.getCars();        
        List<CarDto> dtos = convertCarsToDto(cars);
        Gson gson = new Gson();
        String carsJson = gson.toJson(dtos);
        
        return Response.status(Response.Status.OK)
                    .entity(carsJson)
                    .build();
    }
}
