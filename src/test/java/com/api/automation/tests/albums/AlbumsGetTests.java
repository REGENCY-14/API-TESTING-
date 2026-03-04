package com.api.automation.tests.albums;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
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
 * Test class for Albums GET endpoints
 * Tests GET operations on JSONPlaceholder Albums API
 */
@Feature("API Testing")
@Story("Albums GET Tests")
@DisplayName("Albums GET API Tests")
public class AlbumsGetTests extends BaseTest {

    // HTTP Request Methods
    
    private Response getAllAlbums() {
        return get(Endpoints.ALBUMS);
    }
    
    private Response getAlbumById(int albumId) {
        return get(Endpoints.ALBUMS + "/" + albumId);
    }
    
    private Response getAlbumsByUserId(int userId) {
        return get(Endpoints.ALBUMS, "userId", userId);
    }

    // Test Methods

    /**
     * Test GET /albums endpoint
     * Validates that the API returns a list of albums
     */
    @Test
    @DisplayName("Should retrieve all albums")
    public void testGetAllAlbums() {
        getAllAlbums()
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .header("Content-Type", notNullValue())
                .header("Connection", notNullValue())
                .header("Cache-Control", notNullValue())
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
        getAlbumById(1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .header("Content-Type", notNullValue())
                .header("Connection", notNullValue())
                .header("Etag", notNullValue())
                .body("userId", notNullValue())
                .body("id", equalTo(1))
                .body("title", notNullValue());
    }

    /**
     * Test GET /albums?userId=1 endpoint
     * Validates filtering albums by user ID
     */
    @Test
    @DisplayName("Should retrieve albums by user ID")
    public void testGetAlbumsByUserId() {
        getAlbumsByUserId(1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("userId", everyItem(equalTo(1)));
    }
    
    /**
     * Boundary test: First valid album ID (ID = 1)
     */
    @Test
    @DisplayName("Should retrieve album with first valid boundary ID (1)")
    public void testFirstValidBoundary() {
        getAlbumById(1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("id", equalTo(1))
                .body("userId", notNullValue())
                .body("title", notNullValue());
    }
    
    /**
     * Boundary test: Last valid album ID (ID = 100)
     */
    @Test
    @DisplayName("Should retrieve album with last valid boundary ID (100)")
    public void testLastValidBoundary() {
        getAlbumById(100)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("id", equalTo(100))
                .body("userId", notNullValue())
                .body("title", notNullValue());
    }
    
    /**
     * Boundary test: Invalid ID = 0 (below minimum)
     */
    @Test
    @DisplayName("Should return 404 for album ID 0")
    public void testZeroIdBoundary() {
        getAlbumById(0)
                .then()
                .statusCode(404);
    }
    
    /**
     * Boundary test: Negative ID (invalid)
     */
    @Test
    @DisplayName("Should return 404 for negative album ID")
    public void testNegativeIdBoundary() {
        getAlbumById(-1)
                .then()
                .statusCode(404);
    }
    
    /**
     * Boundary test: ID beyond maximum (ID = 101)
     */
    @Test
    @DisplayName("Should return 404 for album ID beyond maximum (101)")
    public void testBeyondMaxIdBoundary() {
        getAlbumById(101)
                .then()
                .statusCode(404);
    }
    
    // INTENTIONAL FAILURE TESTS - Expected to fail
    
    /**
     * Intentional failure test: Wrong status code expectation
     */
    @Test
    @DisplayName("[SHOULD FAIL] Expects wrong status code for valid album")
    public void testIntentionalFailureWrongStatusCode() {
        getAlbumById(1)
                .then()
                .statusCode(500); // This will fail - actual is 200
    }
    
    /**
     * Intentional failure test: Wrong title expectation
     */
    @Test
    @DisplayName("[SHOULD FAIL] Expects wrong title in album response")
    public void testIntentionalFailureWrongTitle() {
        getAlbumById(1)
                .then()
                .statusCode(200)
                .body("title", equalTo("This title does not exist")); // This will fail
    }
}
