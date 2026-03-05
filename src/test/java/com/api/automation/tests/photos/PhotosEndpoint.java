package com.api.automation.tests.photos;

import io.restassured.response.Response;
import utils.Endpoints;
import com.api.automation.base.BaseTest;

/**
 * Endpoint class for Photos API
 * Encapsulates all HTTP operations related to Photos
 */
public class PhotosEndpoint {

    private BaseTest baseTest = new BaseTest();

    public PhotosEndpoint() {
        baseTest.setup();
    }

    // GET Operations
    
    public Response getAllPhotos() {
        return baseTest.get(Endpoints.PHOTOS);
    }
    
    public Response getPhotoById(int photoId) {
        return baseTest.get(Endpoints.PHOTOS + "/" + photoId);
    }
    
    public Response getPhotosByAlbumId(int albumId) {
        return baseTest.get(Endpoints.PHOTOS, "albumId", albumId);
    }

    // POST Operations
    
    public Response createPhoto(int albumId, String title, String url, String thumbnailUrl) {
        String payload = String.format("{\n" +
                "  \"albumId\": %d,\n" +
                "  \"title\": \"%s\",\n" +
                "  \"url\": \"%s\",\n" +
                "  \"thumbnailUrl\": \"%s\"\n" +
                "}", albumId, title, url, thumbnailUrl);
        return baseTest.post(Endpoints.PHOTOS, payload);
    }

    // PUT Operations
    
    public Response updatePhoto(int photoId, int albumId, String title, String url, String thumbnailUrl) {
        String payload = String.format("{\n" +
                "  \"albumId\": %d,\n" +
                "  \"id\": %d,\n" +
                "  \"title\": \"%s\",\n" +
                "  \"url\": \"%s\",\n" +
                "  \"thumbnailUrl\": \"%s\"\n" +
                "}", albumId, photoId, title, url, thumbnailUrl);
        return baseTest.put(Endpoints.PHOTOS + "/" + photoId, payload);
    }

    // DELETE Operations
    
    public Response deletePhoto(int photoId) {
        return baseTest.delete(Endpoints.PHOTOS + "/" + photoId);
    }
}
