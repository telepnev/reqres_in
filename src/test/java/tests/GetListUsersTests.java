package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.UserData;
import model.UsersResponse;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.*;

public class GetListUsersTests {

    private final String BASE_URL = "https://reqres.in/api/users";
    // ObjectMapper из библиотеки com.fasterxml.jackson.databind, мапит JSON с нашими классами, тобишь с обьектами
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetUsersList() throws Exception {
        // зарос Get
        Response response = RestAssured
                .given()
                .when()
                // тут хедары и всякая остальная инфа
                .get(BASE_URL)
                .then()
                .log().all()
                // ответ
                .statusCode(200)
                .extract().response();

        // Десериализация Json - ответа в обьект UsersResponse
        UsersResponse usersResponse = objectMapper.readValue(response.asString(), UsersResponse.class);

        //Проверки
        // "per_page": 6
        assertEquals(6, usersResponse.getData().size(), "Кол-во пользователей не совпадает");
        //"total_pages": 2
        assertEquals(2, usersResponse.getTotal_pages(), "Кол-во страниц не совпадает");
        // "page": 2
        assertEquals(1, usersResponse.getPage(),"Кол-во страниц не совпадает");
        // "total": 12,
        assertEquals(12, usersResponse.getTotal(), "Кол-во всех юзеров не совпадает");


        //  проходим циклом и проверяем поля у юзеров
        for(UserData user : usersResponse.getData()) {

            // Проверяем что почта пользователя заканчивается на @reqres.in
            assertTrue(user.getEmail().endsWith("@reqres.in"),
                    "Email пользователя не совпадает, Email = " + user.getEmail());

            // Проверяем ID юзера
            assertNotNull(user.getId(), "ID юзера вернуло Null");

           // загрузки аватара image.jpg
            assertTrue(user.getAvatar().endsWith("image.jpg"), "Ошибка загрузки аватара");
        }
    }

    @Test
    public void simpleTest() {
        Response response = RestAssured
                .given()
                .when()
                // тут хедары и всякая остальная инфа
                .get(BASE_URL)
                .then()
                .log().all()
                .body("data.first_name[0]", equalTo("George"))
                .statusCode(200)
                .extract().response();
    }
}
