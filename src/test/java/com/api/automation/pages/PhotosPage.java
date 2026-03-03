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
 * Page Object for Photos API endpoints
 * Contains all photo-related API interactions and assertions
 */
public class PhotosPage extends BasePage {
    
    public PhotosPage(RequestSpecification requestSpec) {
        super(requestSpec);
    }
    
    /**
     * Retrieves all photos
     * @return Response object
     */
    public Response getAllPhotos() {
        return get(Endpoints.PHOTOS);
    }
    
    /**
     * Retrieves a photo by ID
     * @param photoId The photo ID
     * @return Response object
     */
    public Response getPhotoById(int photoId) {
        return get(Endpoints.PHOTOS + "/" + photoId);
    }
    
    /**
     * Retrieves photos filtered by album ID
     * @param albumId The album ID
     * @return Response object
     */
    public Response getPhotosByAlbumId(int albumId) {
        return get(Endpoints.PHOTOS, "albumId", albumId);
    }
    
    /**
     * Creates a new photo
     * @param albumId The album ID
     * @param title The photo title
     * @param url The photo URL
     * @param thumbnailUrl The photo thumbnail URL
     * @return Response object
     */
    public Response createPhoto(int albumId, String title, String url, String thumbnailUrl) {
        String payload = String.format("{\n" +
                "  \"albumId\": %d,\n" +
                "  \"title\": \"%s\",\n" +
                "  \"url\": \"%s\",\n" +
                "  \"thumbnailUrl\": \"%s\"\n" +
                "}", albumId, title, url, thumbnailUrl);
        return post(Endpoints.PHOTOS, payload);
    }
    
    /**
     * Updates an existing photo
     * @param photoId The photo ID
     * @param albumId The album ID
     * @param title The photo title
     * @param url The photo URL
     * @param thumbnailUrl The photo thumbnail URL
     * @return Response object
     */
    public Response updatePhoto(int photoId, int albumId, String title, String url, String thumbnailUrl) {
        String payload = String.format("{\n" +
                "  \"albumId\": %d,\n" +
                "  \"id\": %d,\n" +
                "  \"title\": \"%s\",\n" +
                "  \"url\": \"%s\",\n" +
                "  \"thumbnailUrl\": \"%s\"\n" +
                "}", albumId, photoId, title, url, thumbnailUrl);
        return put(Endpoints.PHOTOS + "/" + photoId, payload);
    }
    
    /**
     * Deletes a photo
     * @param photoId The photo ID
     * @return Response object
     */
    public Response deletePhoto(int photoId) {
        return delete(Endpoints.PHOTOS + "/" + photoId);
    }
    
    /**
     * Verifies that all photos response is valid
     */
    public PhotosPage verifyGetAllPhotosResponse() {
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
        return this;
    }
    
    /**
     * Verifies that get photo by ID response is valid
     * @param photoId The photo ID
     */
    public PhotosPage verifyGetPhotoByIdResponse(int photoId) {
        getPhotoById(photoId)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("albumId", notNullValue())
                .body("id", equalTo(photoId))
                .body("title", notNullValue())
                .body("url", notNullValue())
                .body("thumbnailUrl", notNullValue());
        return this;
    }
    
    /**
     * Verifies that get photo with invalid ID returns 404
     * @param photoId The invalid photo ID
     */
    public PhotosPage verifyGetPhotoWithInvalidIdResponse(int photoId) {
        getPhotoById(photoId)
                .then()
                .statusCode(404);
        return this;
    }
    
    /**
     * Verifies that get photos by album ID response is valid
     * @param albumId The album ID
     */
    public PhotosPage verifyGetPhotosByAlbumIdResponse(int albumId) {
        getPhotosByAlbumId(albumId)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("albumId", everyItem(equalTo(albumId)));
        return this;
    }
    
    /**
     * Verifies that create photo response is valid
     * @param albumId The album ID
     * @param title The photo title
     * @param url The photo URL
     * @param thumbnailUrl The photo thumbnail URL
     */
    public PhotosPage verifyCreatePhotoResponse(int albumId, String title, String url, String thumbnailUrl) {
        createPhoto(albumId, title, url, thumbnailUrl)
                .then()
                .statusCode(201)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("albumId", equalTo(albumId))
                .body("title", equalTo(title))
                .body("url", equalTo(url))
                .body("thumbnailUrl", equalTo(thumbnailUrl));
        return this;
    }
    
    /**
     * Verifies that update photo response is valid
     * @param photoId The photo ID
     * @param title The photo title
     * @param url The photo URL
     * @param thumbnailUrl The photo thumbnail URL
     */
    public PhotosPage verifyUpdatePhotoResponse(int photoId, String title, String url, String thumbnailUrl) {
        updatePhoto(photoId, 1, title, url, thumbnailUrl)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("id", equalTo(photoId))
                .body("title", equalTo(title))
                .body("url", equalTo(url))
                .body("thumbnailUrl", equalTo(thumbnailUrl));
        return this;
    }
    
    /**
     * Verifies that delete photo response is valid
     * @param photoId The photo ID
     */
    public PhotosPage verifyDeletePhotoResponse(int photoId) {
        deletePhoto(photoId)
                .then()
                .statusCode(200);
        return this;
    }
}
