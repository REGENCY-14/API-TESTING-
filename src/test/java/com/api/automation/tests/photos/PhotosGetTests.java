package com.api.automation.tests.photos;

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

/**
 * Test class for Photos GET endpoints
 * Tests GET operations on JSONPlaceholder Photos API
 */
@Feature("API Testing")
@Story("Photos GET Tests")
@DisplayName("Photos GET API Tests")
public class PhotosGetTests extends BaseTest {

    // HTTP Request Methods
    
    private Response getAllPhotos() {
        return get(Endpoints.PHOTOS);
    }
    
    private Response getPhotoById(int photoId) {
        return get(Endpoints.PHOTOS + "/" + photoId);
    }
    
    private Response getPhotosByAlbumId(int albumId) {
        return get(Endpoints.PHOTOS, "albumId", albumId);
    }

    // Test Methods

    /**
     * Test GET /photos endpoint
     * Validates that the API returns a list of photos
     */
    @Test
    @DisplayName("Should retrieve all photos")
    public void testGetAllPhotos() {
        getAllPhotos()
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("[0].albumId", notNullValue())
                .body("[0].id", notNullValue())
                .body("[0].title", notNullValue())
                .body("[0].url", notNullValue())
                .body("[0].thumbnailUrl", notNullValue());
    }

    /**
     * Test GET /photos/1 endpoint
     * Validates status code, content type, and response body structure
     */
    @Test
    @DisplayName("Should retrieve photo with id 1")
    public void testGetPhotoById() {
        getPhotoById(1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("albumId", notNullValue())
                .body("id", equalTo(1))
                .body("title", notNullValue())
                .body("url", notNullValue())
                .body("thumbnailUrl", notNullValue());
    }

    /**
     * Test GET /photos?albumId=1 endpoint
     * Validates filtering photos by album ID
     */
    @Test
    @DisplayName("Should retrieve photos by album ID")
    public void testGetPhotosByAlbumId() {
        getPhotosByAlbumId(1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("albumId", everyItem(equalTo(1)));
    }

    /**
     * Test GET /photos with invalid ID
     * Validates error handling for non-existent photo
     */
    @Test
    @DisplayName("Should return empty response for invalid photo ID")
    public void testGetPhotoWithInvalidId() {
        getPhotoById(99999)
                .then()
                .statusCode(404);
    }
}
