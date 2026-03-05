package com.api.automation.tests.comments;

import io.restassured.response.Response;
import utils.Endpoints;
import com.api.automation.base.BaseTest;

/**
 * Endpoint class for Comments API
 * Encapsulates all HTTP operations related to Comments
 */
public class CommentsEndpoint {

    private BaseTest baseTest = new BaseTest();

    public CommentsEndpoint() {
        baseTest.setup();
    }

    // GET Operations
    
    public Response getAllComments() {
        return baseTest.get(Endpoints.COMMENTS);
    }
    
    public Response getCommentById(int commentId) {
        return baseTest.get(Endpoints.COMMENTS + "/" + commentId);
    }
    
    public Response getCommentsByPostId(int postId) {
        return baseTest.get(Endpoints.COMMENTS, "postId", postId);
    }

    // POST Operations
    
    public Response createComment(int postId, String name, String email, String body) {
        String payload = String.format("{\n" +
                "  \"postId\": %d,\n" +
                "  \"name\": \"%s\",\n" +
                "  \"email\": \"%s\",\n" +
                "  \"body\": \"%s\"\n" +
                "}", postId, name, email, body);
        return baseTest.post(Endpoints.COMMENTS, payload);
    }

    // PUT Operations
    
    public Response updateComment(int commentId, int postId, String name, String email, String body) {
        String payload = String.format("{\n" +
                "  \"postId\": %d,\n" +
                "  \"id\": %d,\n" +
                "  \"name\": \"%s\",\n" +
                "  \"email\": \"%s\",\n" +
                "  \"body\": \"%s\"\n" +
                "}", postId, commentId, name, email, body);
        return baseTest.put(Endpoints.COMMENTS + "/" + commentId, payload);
    }

    // DELETE Operations
    
    public Response deleteComment(int commentId) {
        return baseTest.delete(Endpoints.COMMENTS + "/" + commentId);
    }
}
