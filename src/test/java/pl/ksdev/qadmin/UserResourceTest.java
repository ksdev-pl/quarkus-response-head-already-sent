package pl.ksdev.qadmin;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
public class UserResourceTest extends CleanDatabaseTest {

    @Test
    @AsUser
    void shouldGetUsersPage() {
        when()
            .get("/users")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(containsString("Users"));
    }

    @Test
    @AsAdmin
    void shouldGetSingleUser() {
        var userId = createTestUser();

        when()
            .get("/users/" + userId)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(containsString("test@example.com"));
    }

    @Test
    @AsAdmin
    void shouldCreateUser() {
        var userId = createTestUser();

        when()
            .get("/users/" + userId)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(containsString("test@example.com"));
    }

    @Test
    @AsAdmin
    void shouldUpdateUser() {
        var userId = createTestUser();

        given()
            .formParam("username", "updated@example.com")
            .formParam("password", "changed")
        .when()
            .put("/users/" + userId)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(containsString("updated@example.com"));
    }

    @Test
    @AsAdmin
    void shouldDeleteUser() {
        var userId = createTestUser();

        when()
            .delete("/users/" + userId)
        .then()
            .statusCode(HttpStatus.SC_OK);

        when()
            .get("/users/" + userId)
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    private String createTestUser() {
        return
            given()
                .formParam("username", "test@example.com")
                .formParam("password", "secret")
            .when()
                .post("/users")
            .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().path("id").toString();
    }
}
