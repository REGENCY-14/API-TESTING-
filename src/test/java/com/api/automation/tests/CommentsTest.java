package com.api.automation.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.api.automation.base.BaseTest;
import com.api.automation.pages.CommentsPage;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;

/**
 * Test class for Comments API endpoints
 * Tests CRUD operations on JSONPlaceholder Comments API
 * Uses Page Object Model for maintainability
 */
@Feature("API Testing")
@Story("Comments Endpoint Tests")
@DisplayName("Comments API Tests")
public class CommentsTest extends BaseTest {

    private CommentsPage commentsPage;

    /**
     * Initializes the Comments page object before each test
     */
    @BeforeEach
    public void initializePageObjects() {
        commentsPage = new CommentsPage(requestSpec);
    }

    /**
     * Test GET /comments endpoint
     * Validates that the API returns a list of comments
     */
    @Test
    @DisplayName("Should retrieve all comments")
    public void testGetAllComments() {
        commentsPage.verifyGetAllCommentsResponse();
    }

    /**
     * Test GET /comments/1 endpoint
     * Validates status code, content type, and response body structure
     */
    @Test
    @DisplayName("Should retrieve comment with id 1")
    public void testGetCommentById() {
        commentsPage.verifyGetCommentByIdResponse(1);
    }

    /**
     * Test GET /comments with invalid ID
     * Validates error handling for non-existent comment
     */
    @Test
    @DisplayName("Should return empty response for invalid comment ID")
    public void testGetCommentWithInvalidId() {
        commentsPage.verifyGetCommentWithInvalidIdResponse(99999);
    }

    /**
     * Test GET /comments?postId=1 endpoint
     * Validates filtering comments by post ID
     */
    @Test
    @DisplayName("Should retrieve comments by post ID")
    public void testGetCommentsByPostId() {
        commentsPage.verifyGetCommentsByPostIdResponse(1);
    }

    /**
     * Test POST /comments endpoint
     * Tests creating a new comment
     */
    @Test
    @DisplayName("Should create a new comment successfully")
    public void testCreateComment() {
        commentsPage.verifyCreateCommentResponse(1, "Test Comment", "test@example.com", "This is a test comment");
    }

    /**
     * Test PUT /comments/1 endpoint
     * Tests updating an existing comment
     */
    @Test
    @DisplayName("Should update comment successfully")
    public void testUpdateComment() {
        commentsPage.verifyUpdateCommentResponse(1, "Updated Comment", "updated@example.com");
    }

    /**
     * Test DELETE /comments/1 endpoint
     * Tests deleting a comment
     */
    @Test
    @DisplayName("Should delete comment successfully")
    public void testDeleteComment() {
        commentsPage.verifyDeleteCommentResponse(1);
    }
}
