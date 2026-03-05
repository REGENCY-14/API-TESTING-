package com.api.automation.tests.posts;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.api.automation.tests.posts.PostsDataProvider.BEYOND_MAX_ID;
import static com.api.automation.tests.posts.PostsDataProvider.CREATED_STATUS_CODE;
import static com.api.automation.tests.posts.PostsDataProvider.FIRST_VALID_ID;
import static com.api.automation.tests.posts.PostsDataProvider.LAST_VALID_ID;
import static com.api.automation.tests.posts.PostsDataProvider.NEGATIVE_ID;
import static com.api.automation.tests.posts.PostsDataProvider.NON_EXISTENT_FIELD;
import static com.api.automation.tests.posts.PostsDataProvider.SUCCESS_STATUS_CODE;
import static com.api.automation.tests.posts.PostsDataProvider.TEST_POST_BODY;
import static com.api.automation.tests.posts.PostsDataProvider.TEST_POST_TITLE;
import static com.api.automation.tests.posts.PostsDataProvider.UPDATED_POST_BODY;
import static com.api.automation.tests.posts.PostsDataProvider.UPDATED_POST_TITLE;
import static com.api.automation.tests.posts.PostsDataProvider.VALID_POST_ID;
import static com.api.automation.tests.posts.PostsDataProvider.VALID_USER_ID;
import static com.api.automation.tests.posts.PostsDataProvider.WRONG_POST_ID;
import static com.api.automation.tests.posts.PostsDataProvider.WRONG_STATUS_CODE;
import static com.api.automation.tests.posts.PostsDataProvider.ZERO_ID;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;

/**
 * Test class for Posts API
 * Comprehensive tests for all Posts CRUD operations
 */
@Feature("API Testing")
@Story("Posts API Tests")
@DisplayName("Posts API Tests")
public class PostsTest {

    private PostsEndpoint postsEndpoint;

    @BeforeEach
    public void setUp() {
        postsEndpoint = new PostsEndpoint();
    }

    // GET Tests

    /**
     * Test GET /posts endpoint
     * Validates that the API returns a list of posts
     */
    @Test
    @DisplayName("Should retrieve all posts")
    public void testGetAllPosts() {
        postsEndpoint.getAllPosts()
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .contentType(containsString("application/json"))
                .header("Content-Type", notNullValue())
                .header("Connection", notNullValue())
                .header("X-Powered-By", notNullValue())
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
        postsEndpoint.getPostById(VALID_POST_ID)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .contentType(containsString("application/json"))
                .header("Content-Type", notNullValue())
                .header("Connection", notNullValue())
                .header("Etag", notNullValue())
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
        postsEndpoint.getPostsByUserId(VALID_USER_ID)
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
        postsEndpoint.getPostComments(VALID_POST_ID)
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
        postsEndpoint.getPostById(FIRST_VALID_ID)
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
        postsEndpoint.getPostById(LAST_VALID_ID)
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
        postsEndpoint.getPostById(ZERO_ID)
                .then()
                .statusCode(404);
    }
    
    /**
     * Boundary test: Negative ID (invalid)
     */
    @Test
    @DisplayName("Should return 404 for negative post ID")
    public void testNegativeIdBoundary() {
        postsEndpoint.getPostById(NEGATIVE_ID)
                .then()
                .statusCode(404);
    }
    
    /**
     * Boundary test: ID beyond maximum (ID = 101)
     */
    @Test
    @DisplayName("Should return 404 for post ID beyond maximum (101)")
    public void testBeyondMaxIdBoundary() {
        postsEndpoint.getPostById(BEYOND_MAX_ID)
                .then()
                .statusCode(404);
    }

    // POST Tests

    /**
     * Test POST /posts endpoint
     * Tests creating a new post
     */
    @Test
    @DisplayName("Should create a new post successfully")
    public void testCreatePost() {
        postsEndpoint.createPost(VALID_USER_ID, TEST_POST_TITLE, TEST_POST_BODY)
                .then()
                .statusCode(CREATED_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("userId", equalTo(VALID_USER_ID))
                .body("title", equalTo(TEST_POST_TITLE))
                .body("body", equalTo(TEST_POST_BODY));
    }

    // PUT Tests

    /**
     * Test PUT /posts/1 endpoint
     * Tests updating an existing post
     */
    @Test
    @DisplayName("Should update post successfully")
    public void testUpdatePost() {
        postsEndpoint.updatePost(VALID_POST_ID, VALID_USER_ID, UPDATED_POST_TITLE, UPDATED_POST_BODY)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("id", equalTo(VALID_POST_ID))
                .body("title", equalTo(UPDATED_POST_TITLE))
                .body("body", equalTo(UPDATED_POST_BODY));
    }

    // DELETE Tests

    /**
     * Test DELETE /posts/1 endpoint
     * Tests deleting a post
     */
    @Test
    @DisplayName("Should delete post successfully")
    public void testDeletePost() {
        postsEndpoint.deletePost(VALID_POST_ID)
                .then()
                .statusCode(SUCCESS_STATUS_CODE);
    }

    // INTENTIONAL FAILURE TESTS - Expected to fail
    
    /**
     * Intentional failure test: Wrong status code expectation
     */
    @Test
    @DisplayName("[SHOULD FAIL] Expects wrong status code for valid post")
    public void testIntentionalFailureWrongStatusCode() {
        postsEndpoint.getPostById(VALID_POST_ID)
                .then()
                .statusCode(WRONG_STATUS_CODE); // This will fail - actual is 200
    }
    
    /**
     * Intentional failure test: Wrong ID expectation
     */
    @Test
    @DisplayName("[SHOULD FAIL] Expects wrong ID in post response")
    public void testIntentionalFailureWrongData() {
        postsEndpoint.getPostById(VALID_POST_ID)
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
        postsEndpoint.getPostById(VALID_POST_ID)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .body(NON_EXISTENT_FIELD, notNullValue()); // This will fail - field doesn't exist
    }
}
