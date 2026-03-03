package com.api.automation.pages;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.Endpoints;

/**
 * Page Object for Albums API endpoints
 * Contains all album-related API interactions and assertions
 */
public class AlbumsPage extends BasePage {
    
    public AlbumsPage(RequestSpecification requestSpec) {
        super(requestSpec);
    }
    
    /**
     * Retrieves all albums
     * @return Response object
     */
    public Response getAllAlbums() {
        return get(Endpoints.ALBUMS);
    }
    
    /**
     * Retrieves an album by ID
     * @param albumId The album ID
     * @return Response object
     */
    public Response getAlbumById(int albumId) {
        return get(Endpoints.ALBUMS + "/" + albumId);
    }
    
    /**
     * Retrieves albums filtered by user ID
     * @param userId The user ID
     * @return Response object
     */
    public Response getAlbumsByUserId(int userId) {
        return get(Endpoints.ALBUMS, "userId", userId);
    }
    
    /**
     * Creates a new album
     * @param userId The user ID
     * @param title The album title
     * @return Response object
     */
    public Response createAlbum(int userId, String title) {
        String payload = String.format("{\n" +
                "  \"userId\": %d,\n" +
                "  \"title\": \"%s\"\n" +
                "}", userId, title);
        return post(Endpoints.ALBUMS, payload);
    }
    
    /**
     * Updates an existing album
     * @param albumId The album ID
     * @param userId The user ID
     * @param title The album title
     * @return Response object
     */
    public Response updateAlbum(int albumId, int userId, String title) {
        String payload = String.format("{\n" +
                "  \"userId\": %d,\n" +
                "  \"id\": %d,\n" +
                "  \"title\": \"%s\"\n" +
                "}", userId, albumId, title);
        return put(Endpoints.ALBUMS + "/" + albumId, payload);
    }
    
    /**
     * Partially updates an album
     * @param albumId The album ID
     * @param title The album title
     * @return Response object
     */
    public Response patchAlbum(int albumId, String title) {
        String payload = String.format("{\n" +
                "  \"title\": \"%s\"\n" +
                "}", title);
        return patch(Endpoints.ALBUMS + "/" + albumId, payload);
    }
    
    /**
     * Deletes an album
     * @param albumId The album ID
     * @return Response object
     */
    public Response deleteAlbum(int albumId) {
        return delete(Endpoints.ALBUMS + "/" + albumId);
    }
    
    /**
     * Verifies that all albums response is valid
     */
    public AlbumsPage verifyGetAllAlbumsResponse() {
        getAllAlbums()
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("[0].userId", notNullValue())
                .body("[0].id", notNullValue())
                .body("[0].title", notNullValue());
        return this;
    }
    
    /**
     * Verifies that get album by ID response is valid
     * @param albumId The album ID
     */
    public AlbumsPage verifyGetAlbumByIdResponse(int albumId) {
        getAlbumById(albumId)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("userId", notNullValue())
                .body("id", equalTo(albumId))
                .body("title", notNullValue());
        return this;
    }
    
    /**
     * Verifies that get albums by user ID response is valid
     * @param userId The user ID
     */
    public AlbumsPage verifyGetAlbumsByUserIdResponse(int userId) {
        getAlbumsByUserId(userId)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("userId", everyItem(equalTo(userId)));
        return this;
    }
    
    /**
     * Verifies that create album response is valid
     * @param userId The user ID
     * @param title The album title
     */
    public AlbumsPage verifyCreateAlbumResponse(int userId, String title) {
        createAlbum(userId, title)
                .then()
                .statusCode(201)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("userId", equalTo(userId))
                .body("title", equalTo(title));
        return this;
    }
    
    /**
     * Verifies that update album response is valid
     * @param albumId The album ID
     * @param title The album title
     */
    public AlbumsPage verifyUpdateAlbumResponse(int albumId, String title) {
        updateAlbum(albumId, 1, title)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("id", equalTo(albumId))
                .body("title", equalTo(title));
        return this;
    }
    
    /**
     * Verifies that patch album response is valid
     * @param albumId The album ID
     * @param title The album title
     */
    public AlbumsPage verifyPatchAlbumResponse(int albumId, String title) {
        patchAlbum(albumId, title)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("title", equalTo(title));
        return this;
    }
    
    /**
     * Verifies that delete album response is valid
     * @param albumId The album ID
     */
    public AlbumsPage verifyDeleteAlbumResponse(int albumId) {
        deleteAlbum(albumId)
                .then()
                .statusCode(200);
        return this;
    }
}
