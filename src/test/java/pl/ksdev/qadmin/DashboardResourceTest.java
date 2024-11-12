package pl.ksdev.qadmin;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
public class DashboardResourceTest extends CleanDatabaseTest {

    @Test
    @AsUser
    void shouldGetDashboardPage() {
        when()
            .get("/")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(containsString("Dashboard"));
    }
}
