package br.lks.quarkussocial.rest;

import br.lks.quarkussocial.rest.dto.CreateUserRequest;
import br.lks.quarkussocial.rest.dto.UserRequest;
import br.lks.quarkussocial.services.ErrorsValidatorException;
import br.lks.quarkussocial.services.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    public Response create(CreateUserRequest userRequest) {
        CreateUserRequest userCreated;
        try {
            userCreated = userService.create(userRequest);
        } catch (ErrorsValidatorException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getErrors()).build();
        }
        return Response.ok().entity(userCreated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            userService.delete(id);
            return Response.ok().build();
        } catch (ErrorsValidatorException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getErrors()).build();
        }
    }

    @GET
    public Response listAllUsers() {
        List<UserRequest> users = userService.listAllUsers();
        return Response.ok(users).build();
    }

}
