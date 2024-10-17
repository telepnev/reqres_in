package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.SingleUserResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetSingleUserTest {

    private final String BASE_URL = "https://reqres.in/api/users";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getSingleUserTest() throws Exception {
        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL + "/2")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        SingleUserResponse userResponse = objectMapper.readValue(response.asString(), SingleUserResponse.class);

        assertEquals(2, userResponse.getData().getId(), "Id не совпадает");
        assertTrue(userResponse.getData().getEmail().endsWith("@reqres.in"),
                "Почта пользователя не оканчивается на '@reqres.in'");
        assertEquals("Janet", userResponse.getData().getFirst_name(),
                "'first_name' пользователя не совпадает");
        assertEquals("Weaver", userResponse.getData().getLast_name(),
                "'last_nam' пользователя не совпадает");
        assertTrue(userResponse.getData().getAvatar().endsWith("image.jpg"),
                "Ошибка загрузки аватара");

        assertNotNull(userResponse.getSupport().getUrl());
        assertTrue(userResponse.getSupport().getUrl().startsWith("https://reqres.in"));
        assertTrue(userResponse.getSupport().getText().
                equalsIgnoreCase("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }
}
