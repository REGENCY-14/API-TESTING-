package com.api.automation.tests.comments;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.api.automation.base.BaseTest;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import utils.Endpoints;

/**
 * Test class for Comments GET endpoints
 * Tests GET operations on JSONPlaceholder Comments API
 */
@Feature("API Testing")
@Story("Comments GET Tests")
@DisplayName("Comments GET API Tests")
public class CommentsGetTests extends BaseTest {

    // HTTP Request Methods
    
    private Response getAllComments() {
        return get(Endpoints.COMMENTS);
    }
    
    private Response getCommentById(int commentId) {
        return get(Endpoints.COMMENTS + "/" + commentId);
    }
    
    private Response getCommentsByPostId(int postId) {
        return get(Endpoints.COMMENTS, "postId", postId);
    }

    // Test Methods

    /**
     * Test GET /comments endpoint
     * Validates that the API returns a list of comments
     */
    @Test
    @DisplayName("Should retrieve all comments")
    public void testGetAllComments() {
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
    }

    /**
     * Test GET /comments/1 endpoint
     * Validates status code, content type, and response body structure
     */
    @Test
    @DisplayName("Should retrieve comment with id 1")
    public void testGetCommentById() {
        getCommentById(1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("postId", notNullValue())
                .body("id", equalTo(1))
                .body("name", notNullValue())
                .body("email", notNullValue())
                .body("body", notNullValue());
    }

    /**
     * Test GET /comments with invalid ID
     * Validates error handling for non-existent comment
     */
    @Test
    @DisplayName("Should return empty response for invalid comment ID")
    public void testGetCommentWithInvalidId() {
        getCommentById(99999)
                .then()
                .statusCode(404);
    }

    /**
     * Test GET /comments?postId=1 endpoint
     * Validates filtering comments by post ID
     */
    @Test
    @DisplayName("Should retrieve comments by post ID")
    public void testGetCommentsByPostId() {
        getCommentsByPostId(1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("postId", everyItem(equalTo(1)));
    }
}
