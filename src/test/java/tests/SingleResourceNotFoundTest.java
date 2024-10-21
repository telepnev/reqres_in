package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Resource")
@Owner("Artem Eroshenko")
public class SingleResourceNotFoundTest {
    private final String BASE_URL = "https://reqres.in/api/unknown";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Feature("Single Resource")
    @Story("Resource Not Found execption error 404")
    @Test
    public void resourceNotFoundTest() {
        step("Вводим несуществующее ID");
        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL + "/666")
                .then()
                .statusCode(404)
                .log().all()
                .extract().response();

        String body = response.asString();
        step("Проверяем что ответ вернулся пустой ");
        assertEquals("{}", body,"Тело ответа содержит ответ");
    }
}
