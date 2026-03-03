package com.api.automation.tests.photos;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.api.automation.base.BaseTest;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import utils.Endpoints;

/**
 * Test class for Photos POST endpoints
 * Tests POST operations on JSONPlaceholder Photos API
 */
@Feature("API Testing")
@Story("Photos POST Tests")
@DisplayName("Photos POST API Tests")
public class PhotosPostTests extends BaseTest {

    // HTTP Request Methods
    
    private Response createPhoto(int albumId, String title, String url, String thumbnailUrl) {
        String payload = String.format("{\n" +
                "  \"albumId\": %d,\n" +
                "  \"title\": \"%s\",\n" +
                "  \"url\": \"%s\",\n" +
                "  \"thumbnailUrl\": \"%s\"\n" +
                "}", albumId, title, url, thumbnailUrl);
        return post(Endpoints.PHOTOS, payload);
    }

    // Test Methods

    /**
     * Test POST /photos endpoint
     * Tests creating a new photo
     */
    @Test
    @DisplayName("Should create a new photo successfully")
    public void testCreatePhoto() {
        createPhoto(1, "New Test Photo", "https://via.placeholder.com/600/test", "https://via.placeholder.com/150/test")
                .then()
                .statusCode(201)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("albumId", equalTo(1))
                .body("title", equalTo("New Test Photo"))
                .body("url", equalTo("https://via.placeholder.com/600/test"))
                .body("thumbnailUrl", equalTo("https://via.placeholder.com/150/test"));
    }
}
