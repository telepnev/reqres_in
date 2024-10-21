package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.SingleUserResponse;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
@Epic("Resource")
@Owner("Dead Moroz")
public class GetSingleUserTest {

    private final String BASE_URL = "https://reqres.in/api/users";
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Feature("List")
    @Story("Get Single Users")
    @Test
    public void getSingleUserTest() throws Exception {
        step("Отправка Get запроса");
        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL + "/2")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        step("Десериализация Json - ответа в обьект UsersResponse");
        SingleUserResponse userResponse = objectMapper.readValue(response.asString(), SingleUserResponse.class);

        step("Проверяем поле 'Id' ");
        assertEquals(2, userResponse.getData().getId(), "Id не совпадает");
        step("Проверяем поле 'Email' оканчивается на '@reqres.in'");
        assertTrue(userResponse.getData().getEmail().endsWith("@reqres.in"),
                "Почта пользователя не оканчивается на '@reqres.in'");
        step("Проверяем поле 'First_name'");
        assertEquals("Janet", userResponse.getData().getFirst_name(),
                "'first_name' пользователя не совпадает");
        step("Проверяем поле 'Last_name'");
        assertEquals("Weaver", userResponse.getData().getLast_name(),
                "'last_nam' пользователя не совпадает");
        step("Проверяем поле 'Avatar'");
        assertTrue(userResponse.getData().getAvatar().endsWith("image.jpg"),
                "Ошибка загрузки аватара");

        step("Проверяем поле Support");
        assertNotNull(userResponse.getSupport().getUrl());
        assertTrue(userResponse.getSupport().getUrl().startsWith("https://reqres.in"));
        assertTrue(userResponse.getSupport().getText().
                equalsIgnoreCase("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }
}
