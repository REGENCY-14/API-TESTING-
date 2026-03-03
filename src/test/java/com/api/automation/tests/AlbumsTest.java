package com.api.automation.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.api.automation.base.BaseTest;
import com.api.automation.pages.AlbumsPage;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;

/**
 * Test class for Albums API endpoints
 * Tests CRUD operations on JSONPlaceholder Albums API
 * Uses Page Object Model for maintainability
 */
@Feature("API Testing")
@Story("Albums Endpoint Tests")
@DisplayName("Albums API Tests")
public class AlbumsTest extends BaseTest {

    private AlbumsPage albumsPage;

    /**
     * Initializes the Albums page object before each test
     */
    @BeforeEach
    public void initializePageObjects() {
        albumsPage = new AlbumsPage(requestSpec);
    }

    /**
     * Test GET /albums endpoint
     * Validates that the API returns a list of albums
     */
    @Test
    @DisplayName("Should retrieve all albums")
    public void testGetAllAlbums() {
        albumsPage.verifyGetAllAlbumsResponse();
    }

    /**
     * Test GET /albums/1 endpoint
     * Validates status code, content type, and response body structure
     */
    @Test
    @DisplayName("Should retrieve album with id 1")
    public void testGetAlbumById() {
        albumsPage.verifyGetAlbumByIdResponse(1);
    }

    /**
     * Test GET /albums?userId=1 endpoint
     * Validates filtering albums by user ID
     */
    @Test
    @DisplayName("Should retrieve albums by user ID")
    public void testGetAlbumsByUserId() {
        albumsPage.verifyGetAlbumsByUserIdResponse(1);
    }

    /**
     * Test POST /albums endpoint
     * Tests creating a new album
     */
    @Test
    @DisplayName("Should create a new album successfully")
    public void testCreateAlbum() {
        albumsPage.verifyCreateAlbumResponse(1, "New Test Album");
    }

    /**
     * Test PUT /albums/1 endpoint
     * Tests updating an existing album
     */
    @Test
    @DisplayName("Should update album successfully")
    public void testUpdateAlbum() {
        albumsPage.verifyUpdateAlbumResponse(1, "Updated Album Title");
    }

    /**
     * Test PATCH /albums/1 endpoint
     * Tests partial update of an album
     */
    @Test
    @DisplayName("Should patch album successfully")
    public void testPatchAlbum() {
        albumsPage.verifyPatchAlbumResponse(1, "Patched Album Title");
    }

    /**
     * Test DELETE /albums/1 endpoint
     * Tests deleting an album
     */
    @Test
    @DisplayName("Should delete album successfully")
    public void testDeleteAlbum() {
        albumsPage.verifyDeleteAlbumResponse(1);
    }
}
