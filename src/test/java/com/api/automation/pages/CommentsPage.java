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
 * Page Object for Comments API endpoints
 * Contains all comment-related API interactions and assertions
 */
public class CommentsPage extends BasePage {
    
    public CommentsPage(RequestSpecification requestSpec) {
        super(requestSpec);
    }
    
    /**
     * Retrieves all comments
     * @return Response object
     */
    public Response getAllComments() {
        return get(Endpoints.COMMENTS);
    }
    
    /**
     * Retrieves a comment by ID
     * @param commentId The comment ID
     * @return Response object
     */
    public Response getCommentById(int commentId) {
        return get(Endpoints.COMMENTS + "/" + commentId);
    }
    
    /**
     * Retrieves comments filtered by post ID
     * @param postId The post ID
     * @return Response object
     */
    public Response getCommentsByPostId(int postId) {
        return get(Endpoints.COMMENTS, "postId", postId);
    }
    
    /**
     * Creates a new comment
     * @param postId The post ID
     * @param name The comment name/title
     * @param email The comment email
     * @param body The comment body
     * @return Response object
     */
    public Response createComment(int postId, String name, String email, String body) {
        String payload = String.format("{\n" +
                "  \"postId\": %d,\n" +
                "  \"name\": \"%s\",\n" +
                "  \"email\": \"%s\",\n" +
                "  \"body\": \"%s\"\n" +
                "}", postId, name, email, body);
        return post(Endpoints.COMMENTS, payload);
    }
    
    /**
     * Updates an existing comment
     * @param commentId The comment ID
     * @param postId The post ID
     * @param name The comment name/title
     * @param email The comment email
     * @param body The comment body
     * @return Response object
     */
    public Response updateComment(int commentId, int postId, String name, String email, String body) {
        String payload = String.format("{\n" +
                "  \"postId\": %d,\n" +
                "  \"id\": %d,\n" +
                "  \"name\": \"%s\",\n" +
                "  \"email\": \"%s\",\n" +
                "  \"body\": \"%s\"\n" +
                "}", postId, commentId, name, email, body);
        return put(Endpoints.COMMENTS + "/" + commentId, payload);
    }
    
    /**
     * Deletes a comment
     * @param commentId The comment ID
     * @return Response object
     */
    public Response deleteComment(int commentId) {
        return delete(Endpoints.COMMENTS + "/" + commentId);
    }
    
    /**
     * Verifies that all comments response is valid
     */
    public CommentsPage verifyGetAllCommentsResponse() {
        getAllComments()
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("[0].postId", notNullValue())
                .body("[0].id", notNullValue())
                .body("[0].name", notNullValue())
                .body("[0].email", notNullValue())
                .body("[0].body", notNullValue());
        return this;
    }
    
    /**
     * Verifies that get comment by ID response is valid
     * @param commentId The comment ID
     */
    public CommentsPage verifyGetCommentByIdResponse(int commentId) {
        getCommentById(commentId)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("postId", notNullValue())
                .body("id", equalTo(commentId))
                .body("name", notNullValue())
                .body("email", notNullValue())
                .body("body", notNullValue());
        return this;
    }
    
    /**
     * Verifies that get comment with invalid ID returns 404
     * @param commentId The invalid comment ID
     */
    public CommentsPage verifyGetCommentWithInvalidIdResponse(int commentId) {
        getCommentById(commentId)
                .then()
                .statusCode(404);
        return this;
    }
    
    /**
     * Verifies that get comments by post ID response is valid
     * @param postId The post ID
     */
    public CommentsPage verifyGetCommentsByPostIdResponse(int postId) {
        getCommentsByPostId(postId)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("postId", everyItem(equalTo(postId)));
        return this;
    }
    
    /**
     * Verifies that create comment response is valid
     * @param postId The post ID
     * @param name The comment name/title
     * @param email The comment email
     * @param body The comment body
     */
    public CommentsPage verifyCreateCommentResponse(int postId, String name, String email, String body) {
        createComment(postId, name, email, body)
                .then()
                .statusCode(201)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("postId", equalTo(postId))
                .body("name", equalTo(name))
                .body("email", equalTo(email))
                .body("body", equalTo(body));
        return this;
    }
    
    /**
     * Verifies that update comment response is valid
     * @param commentId The comment ID
     * @param name The comment name/title
     * @param email The comment email
     */
    public CommentsPage verifyUpdateCommentResponse(int commentId, String name, String email) {
        updateComment(commentId, 1, name, email, "This comment has been updated")
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("id", equalTo(commentId))
                .body("name", equalTo(name))
                .body("email", equalTo(email));
        return this;
    }
    
    /**
     * Verifies that delete comment response is valid
     * @param commentId The comment ID
     */
    public CommentsPage verifyDeleteCommentResponse(int commentId) {
        deleteComment(commentId)
                .then()
                .statusCode(200);
        return this;
    }

    public static void main(String[] args) {
        // This class is a Page Object and is not meant to be executed directly
    }
}
