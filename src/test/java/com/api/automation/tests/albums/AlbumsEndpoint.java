package com.api.automation.tests.albums;

import io.restassured.response.Response;
import utils.Endpoints;
import com.api.automation.base.BaseTest;

/**
 * Endpoint class for Albums API
 * Encapsulates all HTTP operations related to Albums
 */
public class AlbumsEndpoint {

    private BaseTest baseTest = new BaseTest();

    public AlbumsEndpoint() {
        baseTest.setup();
    }

    // GET Operations
    
    public Response getAllAlbums() {
        return baseTest.get(Endpoints.ALBUMS);
    }
    
    public Response getAlbumById(int albumId) {
        return baseTest.get(Endpoints.ALBUMS + "/" + albumId);
    }
    
    public Response getAlbumsByUserId(int userId) {
        return baseTest.get(Endpoints.ALBUMS, "userId", userId);
    }

    // POST Operations
    
    public Response createAlbum(int userId, String title) {
        String payload = String.format("{\n" +
                "  \"userId\": %d,\n" +
                "  \"title\": \"%s\"\n" +
                "}", userId, title);
        return baseTest.post(Endpoints.ALBUMS, payload);
    }

    // PUT Operations
    
    public Response updateAlbum(int albumId, int userId, String title) {
        String payload = String.format("{\n" +
                "  \"userId\": %d,\n" +
                "  \"id\": %d,\n" +
                "  \"title\": \"%s\"\n" +
                "}", userId, albumId, title);
        return baseTest.put(Endpoints.ALBUMS + "/" + albumId, payload);
    }

    // PATCH Operations
    
    public Response patchAlbum(int albumId, String title) {
        String payload = String.format("{\n" +
                "  \"title\": \"%s\"\n" +
                "}", title);
        return baseTest.patch(Endpoints.ALBUMS + "/" + albumId, payload);
    }

    // DELETE Operations
    
    public Response deleteAlbum(int albumId) {
        return baseTest.delete(Endpoints.ALBUMS + "/" + albumId);
    }
}
