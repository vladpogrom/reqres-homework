package tests;

import helpers.DateFormat;
import helpers.EndPoints;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTestsPositive {
    static Map<String, String> userBody = new HashMap<>();


    @BeforeAll
    static void beforeAll() {
        userBody.put("email", "eve.holt@reqres.in");
        userBody.put("password", "pistol");
    }

    @Test
    @DisplayName("Check all users page test")
    public void getAllUsers() {
        given()
                .param("page", 1)
                .get(EndPoints.URL + EndPoints.USERS)
                .then()
                .statusCode(200)
                .body("data", hasSize(6));
    }

    @Test
    @DisplayName("Create user test")
    public void createUser() {
        Date date = new Date();
        String parseDate = DateFormat.formatDateToString(date, "yyyy-MM-dd'T'HH:mm", "Europe/London");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(userBody)
                .post(EndPoints.URL + EndPoints.USERS);
        response.then().statusCode(201);
        response.then().assertThat().body("createdAt", containsString(parseDate));
    }

    @Test
    @DisplayName("Register user test")
    public void registerUser() {
        given()
                .contentType(ContentType.JSON)
                .body(userBody)
                .post(EndPoints.URL + EndPoints.REGISTER)
                .then()
                .statusCode(200)
                .body("token", is(notNullValue()));
    }
}
