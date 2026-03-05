package com.api.automation.tests.albums;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;

import static com.api.automation.tests.albums.AlbumsDataProvider.*;

/**
 * Test class for Albums API
 * Comprehensive tests for all Albums CRUD operations
 */
@Feature("API Testing")
@Story("Albums API Tests")
@DisplayName("Albums API Tests")
public class AlbumsTest {

    private AlbumsEndpoint albumsEndpoint;

    @BeforeEach
    public void setUp() {
        albumsEndpoint = new AlbumsEndpoint();
    }

    // GET Tests

    /**
     * Test GET /albums endpoint
     * Validates that the API returns a list of albums
     */
    @Test
    @DisplayName("Should retrieve all albums")
    public void testGetAllAlbums() {
        albumsEndpoint.getAllAlbums()
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
        albumsEndpoint.getAlbumById(1)
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
        albumsEndpoint.getAlbumsByUserId(1)
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
        albumsEndpoint.getAlbumById(1)
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
        albumsEndpoint.getAlbumById(100)
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
        albumsEndpoint.getAlbumById(0)
                .then()
                .statusCode(404);
    }
    
    /**
     * Boundary test: Negative ID (invalid)
     */
    @Test
    @DisplayName("Should return 404 for negative album ID")
    public void testNegativeIdBoundary() {
        albumsEndpoint.getAlbumById(-1)
                .then()
                .statusCode(404);
    }
    
    /**
     * Boundary test: ID beyond maximum (ID = 101)
     */
    @Test
    @DisplayName("Should return 404 for album ID beyond maximum (101)")
    public void testBeyondMaxIdBoundary() {
        albumsEndpoint.getAlbumById(101)
                .then()
                .statusCode(404);
    }

    // POST Tests

    /**
     * Test POST /albums endpoint
     * Tests creating a new album
     */
    @Test
    @DisplayName("Should create a new album successfully")
    public void testCreateAlbum() {
        albumsEndpoint.createAlbum(VALID_USER_ID, TEST_ALBUM_TITLE)
                .then()
                .statusCode(CREATED_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("userId", equalTo(VALID_USER_ID))
                .body("title", equalTo(TEST_ALBUM_TITLE));
    }

    // PUT Tests

    /**
     * Test PUT /albums/1 endpoint
     * Tests updating an existing album
     */
    @Test
    @DisplayName("Should update album successfully")
    public void testUpdateAlbum() {
        albumsEndpoint.updateAlbum(VALID_ALBUM_ID, VALID_USER_ID, UPDATED_ALBUM_TITLE)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("id", equalTo(VALID_ALBUM_ID))
                .body("title", equalTo(UPDATED_ALBUM_TITLE));
    }

    // PATCH Tests

    /**
     * Test PATCH /albums/1 endpoint
     * Tests partial update of an album
     */
    @Test
    @DisplayName("Should patch album successfully")
    public void testPatchAlbum() {
        albumsEndpoint.patchAlbum(VALID_ALBUM_ID, PATCHED_ALBUM_TITLE)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("title", equalTo(PATCHED_ALBUM_TITLE));
    }

    // DELETE Tests

    /**
     * Test DELETE /albums/1 endpoint
     * Tests deleting an album
     */
    @Test
    @DisplayName("Should delete album successfully")
    public void testDeleteAlbum() {
        albumsEndpoint.deleteAlbum(VALID_ALBUM_ID)
                .then()
                .statusCode(SUCCESS_STATUS_CODE);
    }

    // INTENTIONAL FAILURE TESTS - Expected to fail
    
    /**
     * Intentional failure test: Wrong status code expectation
     */
    @Test
    @DisplayName("[SHOULD FAIL] Expects wrong status code for valid album")
    public void testIntentionalFailureWrongStatusCode() {
        albumsEndpoint.getAlbumById(1)
                .then()
                .statusCode(500); // This will fail - actual is 200
    }
    
    /**
     * Intentional failure test: Wrong title expectation
     */
    @Test
    @DisplayName("[SHOULD FAIL] Expects wrong title in album response")
    public void testIntentionalFailureWrongTitle() {
        albumsEndpoint.getAlbumById(1)
                .then()
                .statusCode(200)
                .body("title", equalTo("This title does not exist")); // This will fail
    }
}
