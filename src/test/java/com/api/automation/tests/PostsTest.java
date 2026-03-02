package com.api.automation.tests;

import com.api.automation.base.BaseTest;
import com.api.automation.utils.SchemaUtil;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.Endpoints;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.*;

/**
 * Test class for Posts API endpoints
 * Tests CRUD operations on JSONPlaceholder Posts API
 */
@Feature("API Testing")
@Story("Posts Endpoint Tests")
@DisplayName("Posts API Tests")
public class PostsTest extends BaseTest {

    private static final String POST_BY_ID_1 = Endpoints.POSTS + "/1";

    /**
     * Test GET /posts endpoint
     * Validates that the API returns a list of posts
     */
    @Test
    @DisplayName("Should retrieve all posts")
    public void testGetAllPosts() {
        given(requestSpec)
                .when()
                .get(Endpoints.POSTS)
                .then()
                .statusCode(200)
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
        given(requestSpec)
                .when()
                .get(POST_BY_ID_1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("userId", notNullValue())
                .body("id", equalTo(1))
                .body("title", notNullValue())
                .body("body", notNullValue());
    }

    /**
     * Test GET /posts/1 endpoint with response validation
     * Demonstrates extracting and validating specific fields
     */
    @Test
    @DisplayName("Should validate post response fields")
    public void testGetPostWithResponseValidation() {
        Response response = given(requestSpec)
                .when()
                .get(POST_BY_ID_1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .extract()
                .response();

        // Validate that required fields are present and have proper types
        assert response.jsonPath().getInt("userId") > 0 : "userId should be a positive integer";
        assert response.jsonPath().getInt("id") == 1 : "id should be 1";
        assert response.jsonPath().getString("title") != null : "title should not be null";
        assert !response.jsonPath().getString("title").isEmpty() : "title should not be empty";
        assert response.jsonPath().getString("body") != null : "body should not be null";
        assert !response.jsonPath().getString("body").isEmpty() : "body should not be empty";
    }

    /**
     * Test GET /posts/1 endpoint with JSON schema validation
     * Validates response matches the defined JSON schema
     */
    @Test
    @DisplayName("Should validate post against JSON schema")
    public void testGetPostWithSchemaValidation() {
        given(requestSpec)
                .when()
                .get(POST_BY_ID_1)
                .then()
                .statusCode(200)
                .body(matchesJsonSchema(SchemaUtil.getSchema("post-schema.json")));
    }

    /**
     * Test GET /posts?userId=1 endpoint
     * Validates filtering posts by user ID
     */
    @Test
    @DisplayName("Should retrieve posts by user ID")
    public void testGetPostsByUserId() {
        given(requestSpec)
                .queryParam("userId", 1)
                .when()
                .get(Endpoints.POSTS)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("userId", everyItem(equalTo(1)));
    }

    /**
     * Test GET /posts/1/comments endpoint
     * Validates retrieving comments for a specific post
     */
    @Test
    @DisplayName("Should retrieve comments for post")
    public void testGetPostComments() {
        given(requestSpec)
                .when()
                .get(POST_BY_ID_1 + "/comments")
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("postId", everyItem(equalTo(1)))
                .body("[0].id", notNullValue())
                .body("[0].name", notNullValue())
                .body("[0].email", notNullValue())
                .body("[0].body", notNullValue());
    }

    /**
     * Test GET /posts with invalid ID
     * Validates error handling for non-existent post
     */
    @Test
    @DisplayName("Should return empty response for invalid post ID")
    public void testGetPostWithInvalidId() {
        given(requestSpec)
                .when()
                .get(Endpoints.POSTS + "/99999")
                .then()
                .statusCode(404);
    }

    /**
     * Test POST /posts endpoint
     * Tests creating a new post
     */
    @Test
    @DisplayName("Should create a new post successfully")
    public void testCreatePost() {
        String postPayload = "{\n" +
                "  \"userId\": 1,\n" +
                "  \"title\": \"New Test Post\",\n" +
                "  \"body\": \"This is the body of the test post\"\n" +
                "}";

        given(requestSpec)
                .body(postPayload)
                .when()
                .post(Endpoints.POSTS)
                .then()
                .statusCode(201)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("userId", equalTo(1))
                .body("title", equalTo("New Test Post"))
                .body("body", equalTo("This is the body of the test post"));
    }

    /**
     * Test POST /posts endpoint with response extraction
     * Validates that the created post data matches the request
     */
    @Test
    @DisplayName("Should validate post creation with response extraction")
    public void testCreatePostWithResponseValidation() {
        String postPayload = "{\n" +
                "  \"userId\": 1,\n" +
                "  \"title\": \"foo\",\n" +
                "  \"body\": \"bar\"\n" +
                "}";

        Response response = given(requestSpec)
                .body(postPayload)
                .when()
                .post(Endpoints.POSTS)
                .then()
                .statusCode(201)
                .extract()
                .response();

        // Validate response body matches request
        assert response.jsonPath().getInt("userId") == 1 : "userId should be 1";
        assert response.jsonPath().getString("title").equals("foo") : "title should match request";
        assert response.jsonPath().getString("body").equals("bar") : "body should match request";
        assert response.jsonPath().getInt("id") > 0 : "id should be a positive integer";
    }

    /**
     * Test POST /posts endpoint with missing fields
     * Tests validation when required fields are missing
     */
    @Test
    @DisplayName("Should create post even with minimal data")
    public void testCreatePostWithMinimalData() {
        String postPayload = "{\n" +
                "  \"title\": \"Minimal Post\"\n" +
                "}";

        given(requestSpec)
                .body(postPayload)
                .when()
                .post(Endpoints.POSTS)
                .then()
                .statusCode(201)
                .body("title", equalTo("Minimal Post"));
    }

    /**
     * Test PUT /posts/1 endpoint
     * Tests updating an existing post
     */
    @Test
    @DisplayName("Should update post successfully")
    public void testUpdatePost() {
        String updatePayload = "{\n" +
                "  \"userId\": 1,\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"Updated Post Title\",\n" +
                "  \"body\": \"This is the updated body content\"\n" +
                "}";

        given(requestSpec)
                .body(updatePayload)
                .when()
                .put(POST_BY_ID_1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("id", equalTo(1))
                .body("title", equalTo("Updated Post Title"))
                .body("body", equalTo("This is the updated body content"));
    }

    /**
     * Test PUT /posts/1 endpoint with response validation
     * Validates the complete update operation
     */
    @Test
    @DisplayName("Should validate full post update")
    public void testUpdatePostWithValidation() {
        String updatePayload = "{\n" +
                "  \"userId\": 1,\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"Complete Update\",\n" +
                "  \"body\": \"Complete update body\"\n" +
                "}";

        Response response = given(requestSpec)
                .body(updatePayload)
                .when()
                .put(POST_BY_ID_1)
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Validate all fields were updated
        assert response.jsonPath().getInt("id") == 1 : "id should remain 1";
        assert response.jsonPath().getInt("userId") == 1 : "userId should be 1";
        assert response.jsonPath().getString("title").equals("Complete Update") : "title should be updated";
        assert response.jsonPath().getString("body").equals("Complete update body") : "body should be updated";
    }

    /**
     * Test PATCH /posts/1 endpoint
     * Tests partial update of a post
     */
    @Test
    @DisplayName("Should patch post title successfully")
    public void testPatchPostTitle() {
        String patchPayload = "{\n" +
                "  \"title\": \"Patched Post Title\"\n" +
                "}";

        given(requestSpec)
                .body(patchPayload)
                .when()
                .patch(POST_BY_ID_1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("title", equalTo("Patched Post Title"));
    }

    /**
     * Test PATCH /posts/1 endpoint for body field
     * Tests partial update of post body only
     */
    @Test
    @DisplayName("Should patch post body successfully")
    public void testPatchPostBody() {
        String patchPayload = "{\n" +
                "  \"body\": \"Patched post body content\"\n" +
                "}";

        given(requestSpec)
                .body(patchPayload)
                .when()
                .patch(POST_BY_ID_1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("body", equalTo("Patched post body content"));
    }

    /**
     * Test DELETE /posts/1 endpoint
     * Tests deleting a single post
     */
    @Test
    @DisplayName("Should delete post successfully")
    public void testDeletePost() {
        given(requestSpec)
                .when()
                .delete(POST_BY_ID_1)
                .then()
                .statusCode(200);
    }

    /**
     * Test DELETE /posts/{id} for multiple posts
     * Tests deleting different posts by ID
     */
    @Test
    @DisplayName("Should delete multiple posts by ID")
    public void testDeleteMultiplePosts() {
        // Delete post 2
        given(requestSpec)
                .when()
                .delete(Endpoints.POSTS + "/2")
                .then()
                .statusCode(200);

        // Delete post 3
        given(requestSpec)
                .when()
                .delete(Endpoints.POSTS + "/3")
                .then()
                .statusCode(200);
    }

    /**
     * Test POST /posts endpoint with JSON content type validation
     * Ensures proper content type handling
     */
    @Test
    @DisplayName("Should create post with explicit JSON content type")
    public void testCreatePostWithJsonContentType() {
        String postPayload = "{\n" +
                "  \"userId\": 1,\n" +
                "  \"title\": \"Content Type Test\",\n" +
                "  \"body\": \"Testing JSON content type\"\n" +
                "}";

        given(requestSpec)
                .contentType("application/json")
                .body(postPayload)
                .when()
                .post(Endpoints.POSTS)
                .then()
                .statusCode(201)
                .body("title", equalTo("Content Type Test"));
    }

    /**
     * Test response time for GET /posts/1
     * Validates API performance
     */
    @Test
    @DisplayName("Should respond within acceptable time")
    public void testGetPostResponseTime() {
        given(requestSpec)
                .when()
                .get(POST_BY_ID_1)
                .then()
                .statusCode(200)
                .time(lessThan(2000L)); // Should respond in less than 2 seconds
    }
}
