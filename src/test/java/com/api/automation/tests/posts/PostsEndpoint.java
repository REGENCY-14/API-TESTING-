package com.api.automation.tests.posts;

import io.restassured.response.Response;
import utils.Endpoints;
import com.api.automation.base.BaseTest;

/**
 * Endpoint class for Posts API
 * Encapsulates all HTTP operations related to Posts
 */
public class PostsEndpoint {

    private BaseTest baseTest = new BaseTest();

    public PostsEndpoint() {
        baseTest.setup();
    }

    // GET Operations
    
    public Response getAllPosts() {
        return baseTest.get(Endpoints.POSTS);
    }
    
    public Response getPostById(int postId) {
        return baseTest.get(Endpoints.POSTS + "/" + postId);
    }
    
    public Response getPostsByUserId(int userId) {
        return baseTest.get(Endpoints.POSTS, "userId", userId);
    }
    
    public Response getPostComments(int postId) {
        return baseTest.get(Endpoints.POSTS + "/" + postId + "/comments");
    }

    // POST Operations
    
    public Response createPost(int userId, String title, String body) {
        String payload = String.format("{\n" +
                "  \"userId\": %d,\n" +
                "  \"title\": \"%s\",\n" +
                "  \"body\": \"%s\"\n" +
                "}", userId, title, body);
        return baseTest.post(Endpoints.POSTS, payload);
    }

    // PUT Operations
    
    public Response updatePost(int postId, int userId, String title, String body) {
        String payload = String.format("{\n" +
                "  \"userId\": %d,\n" +
                "  \"id\": %d,\n" +
                "  \"title\": \"%s\",\n" +
                "  \"body\": \"%s\"\n" +
                "}", userId, postId, title, body);
        return baseTest.put(Endpoints.POSTS + "/" + postId, payload);
    }

    // DELETE Operations
    
    public Response deletePost(int postId) {
        return baseTest.delete(Endpoints.POSTS + "/" + postId);
    }
}
