package com.api.automation.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.api.automation.base.BaseTest;
import com.api.automation.pages.PhotosPage;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;

/**
 * Test class for Photos API endpoints
 * Tests CRUD operations on JSONPlaceholder Photos API
 * Uses Page Object Model for maintainability
 */
@Feature("API Testing")
@Story("Photos Endpoint Tests")
@DisplayName("Photos API Tests")
public class PhotosTest extends BaseTest {

    private PhotosPage photosPage;

    /**
     * Initializes the Photos page object before each test
     */
    @BeforeEach
    public void initializePageObjects() {
        photosPage = new PhotosPage(requestSpec);
    }

    /**
     * Test GET /photos endpoint
     * Validates that the API returns a list of photos
     */
    @Test
    @DisplayName("Should retrieve all photos")
    public void testGetAllPhotos() {
        photosPage.verifyGetAllPhotosResponse();
    }

    /**
     * Test GET /photos/1 endpoint
     * Validates status code, content type, and response body structure
     */
    @Test
    @DisplayName("Should retrieve photo with id 1")
    public void testGetPhotoById() {
        photosPage.verifyGetPhotoByIdResponse(1);
    }

    /**
     * Test GET /photos?albumId=1 endpoint
     * Validates filtering photos by album ID
     */
    @Test
    @DisplayName("Should retrieve photos by album ID")
    public void testGetPhotosByAlbumId() {
        photosPage.verifyGetPhotosByAlbumIdResponse(1);
    }

    /**
     * Test GET /photos with invalid ID
     * Validates error handling for non-existent photo
     */
    @Test
    @DisplayName("Should return empty response for invalid photo ID")
    public void testGetPhotoWithInvalidId() {
        photosPage.verifyGetPhotoWithInvalidIdResponse(99999);
    }

    /**
     * Test POST /photos endpoint
     * Tests creating a new photo
     */
    @Test
    @DisplayName("Should create a new photo successfully")
    public void testCreatePhoto() {
        photosPage.verifyCreatePhotoResponse(1, "New Test Photo", "https://via.placeholder.com/600/test", "https://via.placeholder.com/150/test");
    }

    /**
     * Test PUT /photos/1 endpoint
     * Tests updating an existing photo
     */
    @Test
    @DisplayName("Should update photo successfully")
    public void testUpdatePhoto() {
        photosPage.verifyUpdatePhotoResponse(1, "Updated Photo Title", "https://via.placeholder.com/600/updated", "https://via.placeholder.com/150/updated");
    }

    /**
     * Test DELETE /photos/1 endpoint
     * Tests deleting a photo
     */
    @Test
    @DisplayName("Should delete photo successfully")
    public void testDeletePhoto() {
        photosPage.verifyDeletePhotoResponse(1);
    }
}
