package com.api.automation.tests;

import com.api.automation.base.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.Endpoints;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Test class for Albums API endpoints
 * Tests CRUD operations on JSONPlaceholder Albums API
 */
@Feature("API Testing")
@Story("Albums Endpoint Tests")
@DisplayName("Albums API Tests")
public class AlbumsTest extends BaseTest {

    private static final String ALBUM_BY_ID_1 = Endpoints.ALBUMS + "/1";

    /**
     * Test GET /albums endpoint
     * Validates that the API returns a list of albums
     */
    @Test
    @DisplayName("Should retrieve all albums")
    public void testGetAllAlbums() {
        given(requestSpec)
                .when()
                .get(Endpoints.ALBUMS)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("[0].userId", notNullValue())
                .body("[0].id", notNullValue())
                .body("[0].title", notNullValue());
    }

    /**
     * Test GET /albums/1 endpoint
     * Validates status code, content type, and response body structure
     */
    @Test
    @DisplayName("Should retrieve album with id 1")
    public void testGetAlbumById() {
        given(requestSpec)
                .when()
                .get(ALBUM_BY_ID_1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("userId", notNullValue())
                .body("id", equalTo(1))
                .body("title", notNullValue());
    }

    /**
     * Test GET /albums/1 endpoint with response validation
     * Demonstrates extracting and validating specific fields
     */
    @Test
    @DisplayName("Should validate album response fields")
    public void testGetAlbumWithResponseValidation() {
        Response response = given(requestSpec)
                .when()
                .get(ALBUM_BY_ID_1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .extract()
                .response();

        // Validate that required fields are present and have proper types
        assert response.jsonPath().getInt("userId") > 0 : "userId should be a positive integer";
        assert response.jsonPath().getInt("id") == 1 : "id should be 1";
        assert response.jsonPath().getString("title") != null : "title should not be null";
        assert !response.jsonPath().getString("title").isEmpty() : "title should not be empty";
    }

    /**
     * Test GET /albums?userId=1 endpoint
     * Validates filtering albums by user ID
     */
    @Test
    @DisplayName("Should retrieve albums by user ID")
    public void testGetAlbumsByUserId() {
        given(requestSpec)
                .queryParam("userId", 1)
                .when()
                .get(Endpoints.ALBUMS)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("userId", everyItem(equalTo(1)));
    }

    /**
     * Test GET /albums/1/photos endpoint
     * Validates retrieving photos for a specific album
     */
    @Test
    @DisplayName("Should retrieve photos for album")
    public void testGetAlbumPhotos() {
        given(requestSpec)
                .when()
                .get(ALBUM_BY_ID_1 + "/photos")
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("albumId", everyItem(equalTo(1)))
                .body("[0].id", notNullValue())
                .body("[0].title", notNullValue())
                .body("[0].url", notNullValue())
                .body("[0].thumbnailUrl", notNullValue());
    }

    /**
     * Test GET /albums with invalid ID
     * Validates error handling for non-existent album
     */
    @Test
    @DisplayName("Should return empty response for invalid album ID")
    public void testGetAlbumWithInvalidId() {
        given(requestSpec)
                .when()
                .get(Endpoints.ALBUMS + "/99999")
                .then()
                .statusCode(404);
    }

    /**
     * Test POST /albums endpoint
     * Tests creating a new album
     */
    @Test
    @DisplayName("Should create a new album successfully")
    public void testCreateAlbum() {
        String albumPayload = "{\n" +
                "  \"userId\": 1,\n" +
                "  \"title\": \"New Test Album\"\n" +
                "}";

        given(requestSpec)
                .body(albumPayload)
                .when()
                .post(Endpoints.ALBUMS)
                .then()
                .statusCode(201)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("userId", equalTo(1))
                .body("title", equalTo("New Test Album"));
    }

    /**
     * Test PUT /albums/1 endpoint
     * Tests updating an existing album
     */
    @Test
    @DisplayName("Should update album successfully")
    public void testUpdateAlbum() {
        String updatePayload = "{\n" +
                "  \"userId\": 1,\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"Updated Album Title\"\n" +
                "}";

        given(requestSpec)
                .body(updatePayload)
                .when()
                .put(ALBUM_BY_ID_1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("id", equalTo(1))
                .body("title", equalTo("Updated Album Title"));
    }

    /**
     * Test PATCH /albums/1 endpoint
     * Tests partial update of an album
     */
    @Test
    @DisplayName("Should patch album successfully")
    public void testPatchAlbum() {
        String patchPayload = "{\n" +
                "  \"title\": \"Patched Album Title\"\n" +
                "}";

        given(requestSpec)
                .body(patchPayload)
                .when()
                .patch(ALBUM_BY_ID_1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("title", equalTo("Patched Album Title"));
    }

    /**
     * Test DELETE /albums/1 endpoint
     * Tests deleting an album
     */
    @Test
    @DisplayName("Should delete album successfully")
    public void testDeleteAlbum() {
        given(requestSpec)
                .when()
                .delete(ALBUM_BY_ID_1)
                .then()
                .statusCode(200);
    }
}
