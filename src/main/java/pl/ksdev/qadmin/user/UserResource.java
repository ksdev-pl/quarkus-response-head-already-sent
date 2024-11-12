package pl.ksdev.qadmin.user;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import pl.ksdev.qadmin.user.dto.UserDto;
import pl.ksdev.qadmin.user.dto.UserForm;

import java.util.List;

@Path("users")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

    @CheckedTemplate(basePath = "user")
    public static class Templates {
        public static native TemplateInstance index(List<UserDto> users);
    }

    @GET
    @RolesAllowed({"admin", "user"})
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance showUsersPage() {
        return Templates.index(userService.getAllUsers());
    }

    @GET
    @Path("{id}")
    @RolesAllowed({"admin", "user"})
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto getUser(@PathParam("id") long id) {
        return userService.getUser(id);
    }

    @POST
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto createUser(@Valid @BeanParam UserForm userForm) {
        return userService.createUser(userForm);
    }

    @PUT
    @Path("{id}")
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto updateUser(@PathParam("id") long id, @Valid @BeanParam UserForm userForm) {
        return userService.updateUser(id, userForm);
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") long id) {
        userService.deleteUser(id);
        return Response.ok().build();
    }
}
