package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Resource")
@Owner("Eroshenko")
@Severity(SeverityLevel.CRITICAL)
public class UserNotFoundTest {
    private final String BASE_URL = "https://reqres.in/api/users";
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Feature("Users")
    @Story("User Not Found execption error 404")
    @Test
    public void userNotFoundTest() throws Exception {
        step("Вводим несуществующее ID");
        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL + "/666")
                .then()
                .log().all()
                .statusCode(404)
                .extract().response();

        String body = response.getBody().asString();
        step("Проверяем что ответ вернулся пустой ");
        assertEquals("{}", body,"Тело ответа содержит ответ");
    }
}
