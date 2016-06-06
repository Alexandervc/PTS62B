/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.resources;

import com.google.gson.Gson;
import domain.Person;
import static dto.DtoConverter.convertPersonToDto;
import dto.LoginUserDto;
import dto.PersonDto;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.PersonService;

/**
 *
 * @author Linda
 */
@Path("/login")
@Stateless
public class PersonResource {
    
    @Inject
    private PersonService service;

    /**
     * Get the person with the given password and username.
     *
     * @param loginUserJson Json-string with username and password.
     * @return If successfull Response with OK and person object;
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(String loginUserJson) {
        // read from Json.
        Gson gson = new Gson();
        LoginUserDto loginUser = gson.fromJson(loginUserJson, 
                LoginUserDto.class);
        Person person = null;
        // search for Person.
        if (loginUser != null) {
            person = this.service.findPersonLogin(loginUser.getUsername(), 
                    loginUser.getPassword());
        }

        // create Json-string from person.
        PersonDto dto = convertPersonToDto(person);
        String jsonPerson = gson.toJson(dto);
        if (person != null) {
            return Response.status(Response.Status.OK)
                    .entity(jsonPerson)
                    .build();
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .build();
    }
}
