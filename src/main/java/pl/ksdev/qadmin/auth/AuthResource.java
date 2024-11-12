package pl.ksdev.qadmin.auth;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.CurrentIdentityAssociation;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Instant;
import java.util.Date;

@Path("auth")
@RequiredArgsConstructor
public class AuthResource {

    private final CurrentIdentityAssociation identity;

    @ConfigProperty(name = "quarkus.http.auth.form.cookie-name")
    String authCookieName;

    @CheckedTemplate(basePath = "auth")
    public static class Templates {
        public static native TemplateInstance login(boolean error);
    }

    @GET
    @Path("login")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance showLoginPage(@QueryParam("error") boolean error) {
        return Templates.login(error);
    }

    @POST
    @Path("logout")
    public Response logout() {
        if (identity.getIdentity().isAnonymous()) {
            throw new NotAuthorizedException("Not authenticated");
        }
        final NewCookie removeCookie = new NewCookie.Builder(authCookieName)
            .maxAge(0)
            .expiry(Date.from(Instant.EPOCH))
            .path("/")
            .build();
        return Response.noContent()
            .cookie(removeCookie)
            .header("HX-Refresh", "true")
            .build();
    }
}
