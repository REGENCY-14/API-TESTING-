package com.api.automation.tests.albums;

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

import static com.api.automation.testdata.albums.AlbumsTestData.*;

/**
 * Test class for Albums POST endpoints
 * Tests POST operations on JSONPlaceholder Albums API
 */
@Feature("API Testing")
@Story("Albums POST Tests")
@DisplayName("Albums POST API Tests")
public class AlbumsPostTests extends BaseTest {

    // HTTP Request Methods
    
    private Response createAlbum(int userId, String title) {
        String payload = String.format("{\n" +
                "  \"userId\": %d,\n" +
                "  \"title\": \"%s\"\n" +
                "}", userId, title);
        return post(Endpoints.ALBUMS, payload);
    }

    // Test Methods

    /**
     * Test POST /albums endpoint
     * Tests creating a new album
     */
    @Test
    @DisplayName("Should create a new album successfully")
    public void testCreateAlbum() {
        createAlbum(VALID_USER_ID, TEST_ALBUM_TITLE)
                .then()
                .statusCode(CREATED_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("userId", equalTo(VALID_USER_ID))
                .body("title", equalTo(TEST_ALBUM_TITLE));
    }
}
