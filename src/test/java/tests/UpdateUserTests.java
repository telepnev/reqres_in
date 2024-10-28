package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.UserJobTitle;
import model.UserModelResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Update User")
@Owner("Artem Eroshenko")
public class UpdateUserTests {

    private final String BASE_URL = "https://reqres.in/api/users";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Story("Update user 200")
    @DisplayName("Обновление пользователя со всеми полями")
    @Description("Пользователь ввел при обновлении свое имя и должность")

    @Test
    public void updateUserTest() throws Exception {

        UserJobTitle user = new UserJobTitle("Evgen", "QA");

        step("Отправка Post запроса");
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put(BASE_URL + "/2")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        step("Десериализация Json - ответа в обьект UserCredentials");
        UserModelResponse userModelResponse = objectMapper.readValue(response.asString(), UserModelResponse.class);

        step("Проверяем поле 'name' ");
        assertEquals( user.getName(), userModelResponse.getName(),
                "Отправленое поле  'name' не равно ответу 'name'");

        step("Проверяем поле 'job' ");
        assertEquals( user.getJob(), userModelResponse.getJob(),
                "Отправленое поле  'job' не равно ответу 'job'");

        step("Проверяем поле 'updatedAt' ");
        assertNotNull(userModelResponse.getUpdatedAt(),
                "Ответ в поле 'createdAt' ПУСТОЙ");
    }

    @Story("Update user 200")
    @DisplayName("Обновление пользователя только с полем Job")
    @Description("Пользователь ввел при обновлении свою должность")
    @Test
    public void updateUserWithoutNameTest() throws Exception {

        UserJobTitle user = new UserJobTitle(null, "QA");

        step("Отправка Post запроса");
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put(BASE_URL + "/2")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        step("Десериализация Json - ответа в обьект UserCredentials");
        UserModelResponse userModelResponse = objectMapper.readValue(response.asString(), UserModelResponse.class);

        step("Проверяем поле 'name' ");
        assertNull(userModelResponse.getName(),
                "Отправленое поле  'name' не равно ответу 'name'");

        step("Проверяем поле 'job' ");
        assertEquals( user.getJob(), userModelResponse.getJob(),
                "Отправленое поле  'job' не равно ответу 'job'");

        step("Проверяем поле 'updatedAt' ");
        assertNotNull(userModelResponse.getUpdatedAt(),
                "Ответ в поле 'createdAt' ПУСТОЙ");
    }

    @Story("Update user 200")
    @DisplayName("Обновление пользователя без поля Job")
    @Description("Пользователь ввел при обновлении только свое имя")
    @Test
    public void updateUserWithoutJobTest() throws Exception {

        UserJobTitle user = new UserJobTitle("Evgen", null);

        step("Отправка Post запроса");
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put(BASE_URL + "/2")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        step("Десериализация Json - ответа в обьект UserCredentials");
        UserModelResponse userModelResponse = objectMapper.readValue(response.asString(), UserModelResponse.class);

        step("Проверяем поле 'name' ");
        assertNull(userModelResponse.getName(),
                "Отправленое поле  'name' не равно ответу 'name'");

        step("Проверяем поле 'job' ");
        assertEquals( user.getJob(), userModelResponse.getJob(),
                "Отправленое поле  'job' не равно ответу 'job'");

        step("Проверяем поле 'updatedAt' ");
        assertNotNull(userModelResponse.getUpdatedAt(),
                "Ответ в поле 'createdAt' ПУСТОЙ");
    }

    @Story("Update user 200")
    @DisplayName("Обновление пользователя с пустыми полями")
    @Description("Пользователь не ввел при обновлении свое имя и должность")

    @Test
    public void updateUserWithoutNameAndJobTest() throws Exception {

        UserJobTitle user = new UserJobTitle();

        step("Отправка Post запроса");
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put(BASE_URL + "/2")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        step("Десериализация Json - ответа в обьект UserCredentials");
        UserModelResponse userModelResponse = objectMapper.readValue(response.asString(), UserModelResponse.class);

        step("Проверяем поле 'name' ");
        assertNull(userModelResponse.getName(),
                "Отправленое поле  'name' не равно ответу null");

        step("Проверяем поле 'job' ");
        assertNull( userModelResponse.getJob(),
                "Отправленое поле  'job' не равно ответу null");

        step("Проверяем поле 'updatedAt' ");
        assertNotNull(userModelResponse.getUpdatedAt(),
                "Ответ в поле 'createdAt' ПУСТОЙ");
    }
}
