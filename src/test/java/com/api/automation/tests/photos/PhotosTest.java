package com.api.automation.tests.photos;

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

import static com.api.automation.tests.photos.PhotosDataProvider.*;

/**
 * Test class for Photos API
 * Comprehensive tests for all Photos CRUD operations
 */
@Feature("API Testing")
@Story("Photos API Tests")
@DisplayName("Photos API Tests")
public class PhotosTest {

    private PhotosEndpoint photosEndpoint;

    @BeforeEach
    public void setUp() {
        photosEndpoint = new PhotosEndpoint();
    }

    // GET Tests

    /**
     * Test GET /photos endpoint
     * Validates that the API returns a list of photos
     */
    @Test
    @DisplayName("Should retrieve all photos")
    public void testGetAllPhotos() {
        photosEndpoint.getAllPhotos()
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
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
        photosEndpoint.getPhotoById(VALID_PHOTO_ID)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("albumId", notNullValue())
                .body("id", equalTo(VALID_PHOTO_ID))
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
        photosEndpoint.getPhotosByAlbumId(VALID_ALBUM_ID)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("albumId", everyItem(equalTo(VALID_ALBUM_ID)));
    }

    /**
     * Test GET /photos with invalid ID
     * Validates error handling for non-existent photo
     */
    @Test
    @DisplayName("Should return 404 for invalid photo ID")
    public void testGetPhotoWithInvalidId() {
        photosEndpoint.getPhotoById(INVALID_PHOTO_ID)
                .then()
                .statusCode(NOT_FOUND_STATUS_CODE);
    }

    // POST Tests

    /**
     * Test POST /photos endpoint
     * Tests creating a new photo
     */
    @Test
    @DisplayName("Should create a new photo successfully")
    public void testCreatePhoto() {
        photosEndpoint.createPhoto(VALID_ALBUM_ID, TEST_PHOTO_TITLE, TEST_PHOTO_URL, TEST_PHOTO_THUMBNAIL_URL)
                .then()
                .statusCode(CREATED_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("albumId", equalTo(VALID_ALBUM_ID))
                .body("title", equalTo(TEST_PHOTO_TITLE))
                .body("url", equalTo(TEST_PHOTO_URL))
                .body("thumbnailUrl", equalTo(TEST_PHOTO_THUMBNAIL_URL));
    }

    // PUT Tests

    /**
     * Test PUT /photos/1 endpoint
     * Tests updating an existing photo
     */
    @Test
    @DisplayName("Should update photo successfully")
    public void testUpdatePhoto() {
        photosEndpoint.updatePhoto(VALID_PHOTO_ID, VALID_ALBUM_ID, UPDATED_PHOTO_TITLE, UPDATED_PHOTO_URL, UPDATED_PHOTO_THUMBNAIL_URL)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("id", equalTo(VALID_PHOTO_ID))
                .body("title", equalTo(UPDATED_PHOTO_TITLE))
                .body("url", equalTo(UPDATED_PHOTO_URL))
                .body("thumbnailUrl", equalTo(UPDATED_PHOTO_THUMBNAIL_URL));
    }

    // DELETE Tests

    /**
     * Test DELETE /photos/1 endpoint
     * Tests deleting a photo
     */
    @Test
    @DisplayName("Should delete photo successfully")
    public void testDeletePhoto() {
        photosEndpoint.deletePhoto(VALID_PHOTO_ID)
                .then()
                .statusCode(SUCCESS_STATUS_CODE);
    }
}
