package com.api.automation.tests.posts;

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

import static com.api.automation.testdata.posts.PostsTestData.*;

/**
 * Test class for Posts GET endpoints
 * Tests GET operations on JSONPlaceholder Posts API
 */
@Feature("API Testing")
@Story("Posts GET Tests")
@DisplayName("Posts GET API Tests")
public class PostsGetTests extends BaseTest {

    // HTTP Request Methods
    
    private Response getAllPosts() {
        return get(Endpoints.POSTS);
    }
    
    private Response getPostById(int postId) {
        return get(Endpoints.POSTS + "/" + postId);
    }
    
    private Response getPostsByUserId(int userId) {
        return get(Endpoints.POSTS, "userId", userId);
    }
    
    private Response getPostComments(int postId) {
        return get(Endpoints.POSTS + "/" + postId + "/comments");
    }

    // Test Methods

    /**
     * Test GET /posts endpoint
     * Validates that the API returns a list of posts
     */
    @Test
    @DisplayName("Should retrieve all posts")
    public void testGetAllPosts() {
        getAllPosts()
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("[0].userId", notNullValue())
                .body("[0].id", notNullValue())
                .body("[0].title", notNullValue())
                .body("[0].body", notNullValue());
    }

    /**
     * Test GET /posts/1 endpoint
     * Validates status code, content type, and response body structure
     */
    @Test
    @DisplayName("Should retrieve post with id 1")
    public void testGetPostById() {
        getPostById(VALID_POST_ID)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("userId", notNullValue())
                .body("id", equalTo(VALID_POST_ID))
                .body("title", notNullValue())
                .body("body", notNullValue());
    }

    /**
     * Test GET /posts?userId=1 endpoint
     * Validates filtering posts by user ID
     */
    @Test
    @DisplayName("Should retrieve posts by user ID")
    public void testGetPostsByUserId() {
        getPostsByUserId(VALID_USER_ID)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("userId", everyItem(equalTo(VALID_USER_ID)));
    }

    /**
     * Test GET /posts/1/comments endpoint
     * Validates retrieving comments for a specific post
     */
    @Test
    @DisplayName("Should retrieve comments for post")
    public void testGetPostComments() {
        getPostComments(VALID_POST_ID)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("postId", everyItem(equalTo(VALID_POST_ID)))
                .body("[0].id", notNullValue())
                .body("[0].name", notNullValue())
                .body("[0].email", notNullValue())
                .body("[0].body", notNullValue());
    }
    
    /**
     * Boundary test: First valid post ID (ID = 1)
     */
    @Test
    @DisplayName("Should retrieve post with first valid boundary ID (1)")
    public void testFirstValidBoundary() {
        getPostById(FIRST_VALID_ID)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("id", equalTo(FIRST_VALID_ID))
                .body("userId", notNullValue())
                .body("title", notNullValue())
                .body("body", notNullValue());
    }
    
    /**
     * Boundary test: Last valid post ID (ID = 100)
     */
    @Test
    @DisplayName("Should retrieve post with last valid boundary ID (100)")
    public void testLastValidBoundary() {
        getPostById(LAST_VALID_ID)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("id", equalTo(LAST_VALID_ID))
                .body("userId", notNullValue())
                .body("title", notNullValue())
                .body("body", notNullValue());
    }
    
    /**
     * Boundary test: Invalid ID = 0 (below minimum)
     */
    @Test
    @DisplayName("Should return 404 for post ID 0")
    public void testZeroIdBoundary() {
        getPostById(ZERO_ID)
                .then()
                .statusCode(404);
    }
    
    /**
     * Boundary test: Negative ID (invalid)
     */
    @Test
    @DisplayName("Should return 404 for negative post ID")
    public void testNegativeIdBoundary() {
        getPostById(NEGATIVE_ID)
                .then()
                .statusCode(404);
    }
    
    /**
     * Boundary test: ID beyond maximum (ID = 101)
     */
    @Test
    @DisplayName("Should return 404 for post ID beyond maximum (101)")
    public void testBeyondMaxIdBoundary() {
        getPostById(BEYOND_MAX_ID)
                .then()
                .statusCode(404);
    }
    
    // INTENTIONAL FAILURE TESTS - Expected to fail
    
    /**
     * Intentional failure test: Wrong status code expectation
     */
    @Test
    @DisplayName("[SHOULD FAIL] Expects wrong status code for valid post")
    public void testIntentionalFailureWrongStatusCode() {
        getPostById(VALID_POST_ID)
                .then()
                .statusCode(WRONG_STATUS_CODE); // This will fail - actual is 200
    }
    
    /**
     * Intentional failure test: Wrong ID expectation
     */
    @Test
    @DisplayName("[SHOULD FAIL] Expects wrong ID in post response")
    public void testIntentionalFailureWrongData() {
        getPostById(VALID_POST_ID)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .body("id", equalTo(WRONG_POST_ID)); // This will fail
    }
    
    /**
     * Intentional failure test: Non-existent field
     */
    @Test
    @DisplayName("[SHOULD FAIL] Expects non-existent field in response")
    public void testIntentionalFailureNonExistentField() {
        getPostById(VALID_POST_ID)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .body(NON_EXISTENT_FIELD, notNullValue()); // This will fail - field doesn't exist
    }
}
