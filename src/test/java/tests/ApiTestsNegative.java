package tests;

import helpers.EndPoints;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class ApiTestsNegative {

    @Test
    @DisplayName("Resource not found test")
    public void resourceNotFound() {
        String unknownResource = "/233";
        given()
                .get(EndPoints.URL + EndPoints.UNKNOWN + unknownResource)
                .then()
                .statusCode(404)
                .body(is("{}"));
    }

    @Test
    @DisplayName("Login unsuccessful")
    public void loginUnsuccessful() {
        String emailBody = "{\"email\": \"eve.holt@reqres.in\"}";
        given()
                .contentType(ContentType.JSON)
                .body(emailBody)
                .post(EndPoints.URL + EndPoints.LOGIN)
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}
