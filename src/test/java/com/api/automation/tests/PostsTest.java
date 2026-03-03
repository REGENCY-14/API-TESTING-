package com.api.automation.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.api.automation.base.BaseTest;
import com.api.automation.pages.PostsPage;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;

/**
 * Test class for Posts API endpoints
 * Tests CRUD operations on JSONPlaceholder Posts API
 * Uses Page Object Model for maintainability
 */
@Feature("API Testing")
@Story("Posts Endpoint Tests")
@DisplayName("Posts API Tests")
public class PostsTest extends BaseTest {

    private PostsPage postsPage;

    /**
     * Initializes the Posts page object before each test
     */
    @BeforeEach
    public void initializePageObjects() {
        postsPage = new PostsPage(requestSpec);
    }

    /**
     * Test GET /posts endpoint
     * Validates that the API returns a list of posts
     */
    @Test
    @DisplayName("Should retrieve all posts")
    public void testGetAllPosts() {
        postsPage.verifyGetAllPostsResponse();
    }

    /**
     * Test GET /posts/1 endpoint
     * Validates status code, content type, and response body structure
     */
    @Test
    @DisplayName("Should retrieve post with id 1")
    public void testGetPostById() {
        postsPage.verifyGetPostByIdResponse(1);
    }

    /**
     * Test GET /posts?userId=1 endpoint
     * Validates filtering posts by user ID
     */
    @Test
    @DisplayName("Should retrieve posts by user ID")
    public void testGetPostsByUserId() {
        postsPage.verifyGetPostsByUserIdResponse(1);
    }

    /**
     * Test GET /posts/1/comments endpoint
     * Validates retrieving comments for a specific post
     */
    @Test
    @DisplayName("Should retrieve comments for post")
    public void testGetPostComments() {
        postsPage.verifyGetPostCommentsResponse(1);
    }

    /**
     * Test POST /posts endpoint
     * Tests creating a new post
     */
    @Test
    @DisplayName("Should create a new post successfully")
    public void testCreatePost() {
        postsPage.verifyCreatePostResponse(1, "New Test Post", "This is the body of the test post");
    }

    /**
     * Test PUT /posts/1 endpoint
     * Tests updating an existing post
     */
    @Test
    @DisplayName("Should update post successfully")
    public void testUpdatePost() {
        postsPage.verifyUpdatePostResponse(1, "Updated Post Title", "This is the updated body content");
    }

    /**
     * Test DELETE /posts/1 endpoint
     * Tests deleting a single post
     */
    @Test
    @DisplayName("Should delete post successfully")
    public void testDeletePost() {
        postsPage.verifyDeletePostResponse(1);
    }
}
