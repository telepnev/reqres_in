package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

public class DeleteUserTest {
    private final String BASE_URL = "https://reqres.in/api/users";
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Feature("Delete user")
    @Story("Delete user 204")
    @Test
    public void deleteUserTest() {
        step("Отправка Delete запроса");
        Response response = RestAssured
                .given()
                .when()
                .delete(BASE_URL + "/2")
                .then()
                .log().all()
                .statusCode(204)
                .extract().response();

    }
}
