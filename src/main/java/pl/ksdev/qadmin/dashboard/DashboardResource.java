package pl.ksdev.qadmin.dashboard;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("")
public class DashboardResource {

    @CheckedTemplate(basePath = "dashboard")
    public static class Templates {
        public static native TemplateInstance index();
    }

    @GET
    @RolesAllowed({"admin", "user"})
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance showDashboardPage() {
        return DashboardResource.Templates.index();
    }
}
