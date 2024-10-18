package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.ResourceData;
import model.ResourceListResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GetListResourceTest {

    private final String BASE_URL = "https://reqres.in/api/unknown";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getListResourceTest() throws Exception {

        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL)
                .then()
                .log().all()
                .extract().response();

        ResourceListResponse resourceListResponse = objectMapper.readValue(response.asString(), ResourceListResponse.class);

        assertEquals(1, resourceListResponse.getPage(), "Кол-во 'total_pages' не совпадает");
        assertEquals(6, resourceListResponse.getPer_page(), "Кол-во 'total_pages' не совпадает");
        assertEquals(12, resourceListResponse.getTotal(), "Кол-во 'total_pages' не совпадает");
        assertEquals(2, resourceListResponse.getTotal_pages(), "Кол-во 'total_pages' не совпадает");

        for (ResourceData resource : resourceListResponse.getData()) {
            assertNotNull(resource.getId(), "Поле 'id' вернуло null");
            assertNotNull(resource.getName(), "Поле 'name' вернуло null");
            assertNotNull(resource.getYear(), "Поле 'year' вернуло null");
            assertNotNull(resource.getColor(), "Поле 'color' вернуло null");
            assertTrue(resource.getPantone_value().matches("\\d{2}-\\d{4}"),
                    "'pantone_value' не попадает под паттерн '00-0000' " );
        }

        assertNotNull(resourceListResponse.getSupport().getUrl());
        assertTrue(resourceListResponse.getSupport().getUrl().startsWith("https://reqres.in"));
        assertTrue(resourceListResponse.getSupport().getText().
                equalsIgnoreCase("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }

}
