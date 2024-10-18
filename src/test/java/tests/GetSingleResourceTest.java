package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.ResourceResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class GetSingleResourceTest {
    private final String BASE_URL = "https://reqres.in/api/unknown";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getSingleResourceTest() throws Exception {
        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL + "/2")
                .then()
                .log().all()
                .extract().response();

        ResourceResponse resourceResponse = objectMapper.readValue(response.asString(), ResourceResponse.class);

        assertNotNull(resourceResponse.getData().getId(), "Поле 'id' вернуло null");
        assertNotNull(resourceResponse.getData().getName(), "Поле 'name' вернуло null");
        assertNotNull(resourceResponse.getData().getYear(), "Поле 'year' вернуло null");
        assertNotNull(resourceResponse.getData().getColor(), "Поле 'color' вернуло null");
        assertTrue(resourceResponse.getData().getPantone_value().matches("\\d{2}-\\d{4}"),
                "'pantone_value' не попадает под паттерн '00-0000' " );


        assertNotNull(resourceResponse.getSupport().getUrl());
        assertTrue(resourceResponse.getSupport().getUrl().startsWith("https://reqres.in"));
        assertTrue(resourceResponse.getSupport().getText().
                equalsIgnoreCase("To keep ReqRes free, contributions towards server costs are appreciated!"));

    }
}
