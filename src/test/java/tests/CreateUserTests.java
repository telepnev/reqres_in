package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.UserJobTitle;
import model.UserModelResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Create User")
@Owner("Artem Eroshenko")
@Severity(SeverityLevel.CRITICAL)
public class CreateUserTests {
    private final String BASE_URL = "https://reqres.in/api/users";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Story("Create user 201")
    @DisplayName("Создание пользователя со всеми полями")
    @Description("Пользователь ввел при создании свое имя и должность")
    @Test
    public void createUserTest() throws Exception{

        UserJobTitle user = new UserJobTitle("Evgen", "QA");

        step("Отправка Post запроса");
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(BASE_URL)
                .then()
                .log().all()
                .statusCode(201)
                .extract().response();

        step("Десериализация Json - ответа в обьект UserCredentials");
        UserModelResponse userModelResponse = objectMapper.readValue(response.asString(), UserModelResponse.class);

        step("Проверяем поле 'name' ");
        assertEquals( user.getName(), userModelResponse.getName(),
                "Отправленое поле  'name' не равно ответу 'name'");

        step("Проверяем поле 'job' ");
        assertEquals( user.getJob(), userModelResponse.getJob(),
                "Отправленое поле  'job' не равно ответу 'job'");

        step("Проверяем поле 'id' ");
        assertNotNull(userModelResponse.getId(),
                "Ответ в поле 'id' ПУСТОЙ");

        step("Проверяем поле 'createdAt' ");
        assertNotNull(userModelResponse.getCreatedAt(),
                "Ответ в поле 'createdAt' ПУСТОЙ");
    }

    @Story("Create user 201")
    @DisplayName("Создание пользователя только с полем Name")
    @Description("Пользователь ввел только свое имя")
    @Test
    public void createUserWithoutJobTest() throws Exception{

        UserJobTitle user = new UserJobTitle("Evgen", null);

        step("Отправка Post запроса");
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(BASE_URL)
                .then()
                .log().all()
                .statusCode(201)
                .extract().response();

        step("Десериализация Json - ответа в обьект UserCredentials");
        UserModelResponse userModelResponse = objectMapper.readValue(response.asString(), UserModelResponse.class);

        step("Проверяем поле 'name' ");
        assertEquals( user.getName(), userModelResponse.getName(),
                "Отправленое поле  'name' не равно ответу 'name'");

        step("Проверяем поле 'job' ");
        assertNull(userModelResponse.getJob(),
                "Отправленое поле  'job' не равно null");

        step("Проверяем поле 'id' ");
        assertNotNull(userModelResponse.getId(),
                "Ответ в поле 'id' ПУСТОЙ");

        step("Проверяем поле 'createdAt' ");
        assertNotNull(userModelResponse.getCreatedAt(),
                "Ответ в поле 'createdAt' ПУСТОЙ");
    }

    @Story("Create user 201")
    @DisplayName("Создание пользователя только с полем Name")
    @Description("Пользователь ввел только свое имя")
    @Test
    public void createUserWithoutNameTest() throws Exception{

        UserJobTitle user = new UserJobTitle(null, "AQA");

        step("Отправка Post запроса");
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(BASE_URL)
                .then()
                .log().all()
                .statusCode(201)
                .extract().response();

        step("Десериализация Json - ответа в обьект UserCredentials");
        UserModelResponse userModelResponse = objectMapper.readValue(response.asString(), UserModelResponse.class);

        step("Проверяем поле 'name' ");
        assertNull(userModelResponse.getName(),
                "Отправленое поле  'name' не равно null");

        step("Проверяем поле 'job' ");
        assertEquals( user.getJob(), userModelResponse.getJob(),
                "Отправленое поле  'job' не равно ответу 'job'");

        step("Проверяем поле 'id' ");
        assertNotNull(userModelResponse.getId(),
                "Ответ в поле 'id' ПУСТОЙ");

        step("Проверяем поле 'createdAt' ");
        assertNotNull(userModelResponse.getCreatedAt(),
                "Ответ в поле 'createdAt' ПУСТОЙ");
    }
}
