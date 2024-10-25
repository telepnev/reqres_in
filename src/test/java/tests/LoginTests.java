package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.UserCredentialsRequest;
import model.UserCredentialsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoginTests {
    private final String BASE_URL = "https://reqres.in/api/login";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("Авторизация пользователя")
    @Description("Авторизация пользователя с корректными кредами и не корректными кредами")
    @ParameterizedTest
    @CsvSource({
            "'eve.holt@reqres.in', 'pistol', true",
            "'eve.holt@reqres.in', '', false",
            "'eve.holt@reqres.in', '1111', false",                       // нет проверки логина и пароля, оставлю так
            "'eve.holt@reqres.in', 'hfd23^@%!^G1132^&', false"
    })
    public void testLoginUser(String email, String password, Boolean isSuccessful) throws JsonProcessingException {
        UserCredentialsRequest user = new UserCredentialsRequest();
        user.setEmail(email);
        user.setPassword(password);

        step("Отправляем запрос с данными. Post");
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(BASE_URL)
                .then()
                .log().all()
                .extract().response();

        if (isSuccessful) {
            UserCredentialsResponse userCredentialsResponse =
                    objectMapper.readValue(response.asString(),UserCredentialsResponse.class);
            step("Проверка авторизации, статус 200");
            assertEquals(200, response.getStatusCode(), "Статус код не соответствует 200");

            step("Проверяем что поле TOKEN не пустое");
            assertNotNull(userCredentialsResponse.getToken());
        } else {
            UserCredentialsResponse userCredentialsResponse =
                    objectMapper.readValue(response.asString(),UserCredentialsResponse.class);
            step("Проверка авторизации, статус 400");
            assertEquals(400, response.getStatusCode(), "Статус код не соответствует 400");

            step("Проверяем сообщение ошибки");
            assertNotNull(userCredentialsResponse.getError());
            assertEquals("Missing password", userCredentialsResponse.getError());
        }
    }
}
