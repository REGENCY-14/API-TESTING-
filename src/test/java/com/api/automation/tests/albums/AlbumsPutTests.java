package com.api.automation.tests.albums;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.api.automation.base.BaseTest;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import utils.Endpoints;

import static com.api.automation.testdata.albums.AlbumsTestData.*;

/**
 * Test class for Albums PUT endpoints
 * Tests PUT operations on JSONPlaceholder Albums API
 */
@Feature("API Testing")
@Story("Albums PUT Tests")
@DisplayName("Albums PUT API Tests")
public class AlbumsPutTests extends BaseTest {

    // HTTP Request Methods
    
    private Response updateAlbum(int albumId, int userId, String title) {
        String payload = String.format("{\n" +
                "  \"userId\": %d,\n" +
                "  \"id\": %d,\n" +
                "  \"title\": \"%s\"\n" +
                "}", userId, albumId, title);
        return put(Endpoints.ALBUMS + "/" + albumId, payload);
    }

    // Test Methods

    /**
     * Test PUT /albums/1 endpoint
     * Tests updating an existing album
     */
    @Test
    @DisplayName("Should update album successfully")
    public void testUpdateAlbum() {
        updateAlbum(VALID_ALBUM_ID, VALID_USER_ID, UPDATED_ALBUM_TITLE)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("id", equalTo(VALID_ALBUM_ID))
                .body("title", equalTo(UPDATED_ALBUM_TITLE));
    }
}
