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
 * Test class for Albums PATCH endpoints
 * Tests PATCH operations on JSONPlaceholder Albums API
 */
@Feature("API Testing")
@Story("Albums PATCH Tests")
@DisplayName("Albums PATCH API Tests")
public class AlbumsPatchTests extends BaseTest {

    // HTTP Request Methods
    
    private Response patchAlbum(int albumId, String title) {
        String payload = String.format("{\n" +
                "  \"title\": \"%s\"\n" +
                "}", title);
        return patch(Endpoints.ALBUMS + "/" + albumId, payload);
    }

    // Test Methods

    /**
     * Test PATCH /albums/1 endpoint
     * Tests partial update of an album
     */
    @Test
    @DisplayName("Should patch album successfully")
    public void testPatchAlbum() {
        patchAlbum(VALID_ALBUM_ID, PATCHED_ALBUM_TITLE)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("title", equalTo(PATCHED_ALBUM_TITLE));
    }
}
