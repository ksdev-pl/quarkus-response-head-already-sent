package pl.ksdev.qadmin;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.authentication.FormAuthConfig;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class AuthResourceTest extends CleanDatabaseTest {

    public static final FormAuthConfig FORM_AUTH_CONFIG = new FormAuthConfig(
        "/j_security_check",
        "j_username",
        "j_password"
    );

    @ConfigProperty(name = "quarkus.http.auth.form.cookie-name")
    String authCookieName;

    @Test
    void shouldAccessLoginWhenAnonymous() {
        when()
            .get("/auth/login")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(containsString("Login"));
    }

    @Test
    void shouldNotAccessDashboardWhenAnonymous() {
        given()
            .redirects().follow(false)
        .when()
            .get("/")
        .then()
            .statusCode(HttpStatus.SC_MOVED_TEMPORARILY)
            .header("Location", containsString("/auth/login"));
    }

    @Test
    void shouldLoginUsingCorrectCredentials() {
        given()
            .auth().form("admin@example.com", "secret", FORM_AUTH_CONFIG)
        .when()
            .get("/")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(containsString("Users"));
    }

    @Test
    void shouldLogoutWhenAuthenticated() {
        given()
            .auth().form("admin@example.com", "secret", FORM_AUTH_CONFIG)
        .when()
            .post("/auth/logout")
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
            .header("HX-Refresh", is("true"))
            .cookie(authCookieName, is(""));
    }

    @Test
    void shouldNotLogoutWhenNotAuthenticated() {
        given()
            .when()
            .post("/auth/logout")
        .then()
            .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }
}
