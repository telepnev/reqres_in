package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.UserCredentialsRequest;
import model.UserCredentialsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@Epic("Регистрация пользователя")
@Owner("Artem Eroshenko")
@Severity(SeverityLevel.CRITICAL)
public class RegisterUserTests {
    private final String BASE_URL = "https://reqres.in/api/register";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Story("Register user 200")
    @DisplayName("Регистрация пользователя")
    @Description("Регистрация пользователя с корректными и не корректными кредами")
    @ParameterizedTest
    @CsvSource({
            "'eve.holt@reqres.in', 'pistol', true",
            "'michael.lawson@reqres.in', '', false"

    })
    public void registerTest(String email, String password, Boolean isSuccessful) throws JsonProcessingException {
        UserCredentialsRequest request = new UserCredentialsRequest();
        request.setEmail(email);
        request.setPassword(password);

        step("Отправляем запрос с данными. Post");
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(BASE_URL)
                .then()
                .log().all()
                .extract().response();

        if (isSuccessful) {
            step("Проверка регистраиции, статус 200");
            UserCredentialsResponse registerResponse = objectMapper.readValue(response.asString(), UserCredentialsResponse.class);
            assertEquals(200, response.getStatusCode(), "Статус код не соответствует 200");

            step("Проверяем что поле ID не пустое");
            assertNotNull(registerResponse.getId());

            step("Проверяем что поле TOKEN не пустое");
            assertNotNull(registerResponse.getToken());

        } else {
            step("Проверка неуспешной регистрации, статус 400");
            UserCredentialsResponse registerResponse = objectMapper.readValue(response.asString(), UserCredentialsResponse.class);
            assertEquals(400, response.getStatusCode(), "Статус код не соответствует 400");

            String errorMessage = registerResponse.getError();

            step("Проверяем сообщение ошибки");
            assertNotNull(registerResponse.getError());
            assertEquals("Missing password", errorMessage);
        }
    }

    @DisplayName("Не успешная регистрация пользователя")
    @Description("Регистрация пользователя с не корректными кредами")
    @ParameterizedTest
    @CsvSource({
            "' ', 'pistol'",
            "' ', ' ', false",
            "'george666.edwards@@gmail.com', 'pistol'"
    })
    public void unsuccessfulRegisterTest(String email, String password) throws JsonProcessingException {
        UserCredentialsRequest request = new UserCredentialsRequest();
        request.setEmail(email);
        request.setPassword(password);
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(BASE_URL)
                .then()
                .log().all()
                .extract().response();

            step("Проверка неуспешной регистрации, статус 400");
            UserCredentialsResponse registerRequest = objectMapper.readValue(response.asString(), UserCredentialsResponse.class);
            assertEquals(400, response.getStatusCode(), "Статус код не соответствует 400");

            String errorMessage = registerRequest.getError();

            step("Проверяем сообщение ошибки");
            assertNotNull(registerRequest.getError());
            assertEquals("Note: Only defined users succeed registration", registerRequest.getError());
        }
    }



