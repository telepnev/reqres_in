package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SingleResourceNotFoundTest {
    private final String BASE_URL = "https://reqres.in/api/unknown";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void resourceNotFoundTest() {
        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL + "/666")
                .then()
                .log().all()
                .extract().response();

        String body = response.asString();

        assertEquals("{}", body,"Тело ответа содержит ответ");
    }
}
