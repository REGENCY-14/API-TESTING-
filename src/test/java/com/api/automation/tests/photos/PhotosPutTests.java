package com.api.automation.tests.photos;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.api.automation.base.BaseTest;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import utils.Endpoints;

/**
 * Test class for Photos PUT endpoints
 * Tests PUT operations on JSONPlaceholder Photos API
 */
@Feature("API Testing")
@Story("Photos PUT Tests")
@DisplayName("Photos PUT API Tests")
public class PhotosPutTests extends BaseTest {

    // HTTP Request Methods
    
    private Response updatePhoto(int photoId, int albumId, String title, String url, String thumbnailUrl) {
        String payload = String.format("{\n" +
                "  \"albumId\": %d,\n" +
                "  \"id\": %d,\n" +
                "  \"title\": \"%s\",\n" +
                "  \"url\": \"%s\",\n" +
                "  \"thumbnailUrl\": \"%s\"\n" +
                "}", albumId, photoId, title, url, thumbnailUrl);
        return put(Endpoints.PHOTOS + "/" + photoId, payload);
    }

    // Test Methods

    /**
     * Test PUT /photos/1 endpoint
     * Tests updating an existing photo
     */
    @Test
    @DisplayName("Should update photo successfully")
    public void testUpdatePhoto() {
        updatePhoto(1, 1, "Updated Photo Title", "https://via.placeholder.com/600/updated", "https://via.placeholder.com/150/updated")
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("id", equalTo(1))
                .body("title", equalTo("Updated Photo Title"))
                .body("url", equalTo("https://via.placeholder.com/600/updated"))
                .body("thumbnailUrl", equalTo("https://via.placeholder.com/150/updated"));
    }
}
