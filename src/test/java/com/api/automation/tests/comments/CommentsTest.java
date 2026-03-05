package com.api.automation.tests.comments;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;

import static com.api.automation.tests.comments.CommentsDataProvider.*;

/**
 * Test class for Comments API
 * Comprehensive tests for all Comments CRUD operations
 */
@Feature("API Testing")
@Story("Comments API Tests")
@DisplayName("Comments API Tests")
public class CommentsTest {

    private CommentsEndpoint commentsEndpoint;

    @BeforeEach
    public void setUp() {
        commentsEndpoint = new CommentsEndpoint();
    }

    // GET Tests

    /**
     * Test GET /comments endpoint
     * Validates that the API returns a list of comments
     */
    @Test
    @DisplayName("Should retrieve all comments")
    public void testGetAllComments() {
        commentsEndpoint.getAllComments()
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
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
        commentsEndpoint.getCommentById(VALID_COMMENT_ID)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("postId", notNullValue())
                .body("id", equalTo(VALID_COMMENT_ID))
                .body("name", notNullValue())
                .body("email", notNullValue())
                .body("body", notNullValue());
    }

    /**
     * Test GET /comments with invalid ID
     * Validates error handling for non-existent comment
     */
    @Test
    @DisplayName("Should return 404 for invalid comment ID")
    public void testGetCommentWithInvalidId() {
        commentsEndpoint.getCommentById(INVALID_COMMENT_ID)
                .then()
                .statusCode(NOT_FOUND_STATUS_CODE);
    }

    /**
     * Test GET /comments?postId=1 endpoint
     * Validates filtering comments by post ID
     */
    @Test
    @DisplayName("Should retrieve comments by post ID")
    public void testGetCommentsByPostId() {
        commentsEndpoint.getCommentsByPostId(VALID_POST_ID)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("postId", everyItem(equalTo(VALID_POST_ID)));
    }

    // POST Tests

    /**
     * Test POST /comments endpoint
     * Tests creating a new comment
     */
    @Test
    @DisplayName("Should create a new comment successfully")
    public void testCreateComment() {
        commentsEndpoint.createComment(VALID_POST_ID, TEST_COMMENT_NAME, TEST_COMMENT_EMAIL, TEST_COMMENT_BODY)
                .then()
                .statusCode(CREATED_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("postId", equalTo(VALID_POST_ID))
                .body("name", equalTo(TEST_COMMENT_NAME))
                .body("email", equalTo(TEST_COMMENT_EMAIL))
                .body("body", equalTo(TEST_COMMENT_BODY));
    }

    // PUT Tests

    /**
     * Test PUT /comments/1 endpoint
     * Tests updating an existing comment
     */
    @Test
    @DisplayName("Should update comment successfully")
    public void testUpdateComment() {
        commentsEndpoint.updateComment(VALID_COMMENT_ID, VALID_POST_ID, UPDATED_COMMENT_NAME, UPDATED_COMMENT_EMAIL, UPDATED_COMMENT_BODY)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("id", equalTo(VALID_COMMENT_ID))
                .body("name", equalTo(UPDATED_COMMENT_NAME))
                .body("email", equalTo(UPDATED_COMMENT_EMAIL));
    }

    // DELETE Tests

    /**
     * Test DELETE /comments/1 endpoint
     * Tests deleting a comment
     */
    @Test
    @DisplayName("Should delete comment successfully")
    public void testDeleteComment() {
        commentsEndpoint.deleteComment(VALID_COMMENT_ID)
                .then()
                .statusCode(SUCCESS_STATUS_CODE);
    }
}
