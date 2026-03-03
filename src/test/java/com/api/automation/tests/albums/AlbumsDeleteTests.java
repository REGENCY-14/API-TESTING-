package com.api.automation.tests.albums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.api.automation.base.BaseTest;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import utils.Endpoints;

import static com.api.automation.testdata.albums.AlbumsTestData.*;

/**
 * Test class for Albums DELETE endpoints
 * Tests DELETE operations on JSONPlaceholder Albums API
 */
@Feature("API Testing")
@Story("Albums DELETE Tests")
@DisplayName("Albums DELETE API Tests")
public class AlbumsDeleteTests extends BaseTest {

    // HTTP Request Methods
    
    private Response deleteAlbum(int albumId) {
        return delete(Endpoints.ALBUMS + "/" + albumId);
    }

    // Test Methods

    /**
     * Test DELETE /albums/1 endpoint
     * Tests deleting an album
     */
    @Test
    @DisplayName("Should delete album successfully")
    public void testDeleteAlbum() {
        deleteAlbum(VALID_ALBUM_ID)
                .then()
                .statusCode(SUCCESS_STATUS_CODE);
    }
}
