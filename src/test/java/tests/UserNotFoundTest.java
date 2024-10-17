package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserNotFoundTest {
    private final String BASE_URL = "https://reqres.in/api/users";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void userNotFoundTest() throws Exception {
        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL + "/666")
                .then()
                .log().all()
                .statusCode(404)
                .extract().response();

        String body = response.getBody().asString();

        assertEquals("{}", body,"Тело ответа содержит ответ");
    }
}
