package com.api.automation.tests;

import com.api.automation.base.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.Endpoints;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Test class for Comments API endpoints
 * Tests CRUD operations on JSONPlaceholder Comments API
 */
@Feature("API Testing")
@Story("Comments Endpoint Tests")
@DisplayName("Comments API Tests")
public class CommentsTest extends BaseTest {

    private static final String COMMENT_BY_ID_1 = Endpoints.COMMENTS + "/1";

    /**
     * Test GET /comments endpoint
     * Validates that the API returns a list of comments
     */
    @Test
    @DisplayName("Should retrieve all comments")
    public void testGetAllComments() {
        given(requestSpec)
                .when()
                .get(Endpoints.COMMENTS)
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
        given(requestSpec)
                .when()
                .get(COMMENT_BY_ID_1)
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
     * Test GET /comments/1 endpoint with response validation
     * Demonstrates extracting and validating specific fields
     */
    @Test
    @DisplayName("Should validate comment response fields")
    public void testGetCommentWithResponseValidation() {
        Response response = given(requestSpec)
                .when()
                .get(COMMENT_BY_ID_1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .extract()
                .response();

        // Validate that required fields are present and have proper types
        assert response.jsonPath().getInt("postId") > 0 : "postId should be a positive integer";
        assert response.jsonPath().getInt("id") == 1 : "id should be 1";
        assert response.jsonPath().getString("name") != null : "name should not be null";
        assert response.jsonPath().getString("email") != null : "email should not be null";
        assert response.jsonPath().getString("email").contains("@") : "email should be valid";
        assert response.jsonPath().getString("body") != null : "body should not be null";
    }

    /**
     * Test GET /comments?postId=1 endpoint
     * Validates filtering comments by post ID
     */
    @Test
    @DisplayName("Should retrieve comments by post ID")
    public void testGetCommentsByPostId() {
        given(requestSpec)
                .queryParam("postId", 1)
                .when()
                .get(Endpoints.COMMENTS)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("postId", everyItem(equalTo(1)));
    }

    /**
     * Test GET /comments with invalid ID
     * Validates error handling for non-existent comment
     */
    @Test
    @DisplayName("Should return empty response for invalid comment ID")
    public void testGetCommentWithInvalidId() {
        given(requestSpec)
                .when()
                .get(Endpoints.COMMENTS + "/99999")
                .then()
                .statusCode(404);
    }

    /**
     * Test POST /comments endpoint
     * Tests creating a new comment
     */
    @Test
    @DisplayName("Should create a new comment successfully")
    public void testCreateComment() {
        String commentPayload = "{\n" +
                "  \"postId\": 1,\n" +
                "  \"name\": \"Test Comment\",\n" +
                "  \"email\": \"test@example.com\",\n" +
                "  \"body\": \"This is a test comment\"\n" +
                "}";

        given(requestSpec)
                .body(commentPayload)
                .when()
                .post(Endpoints.COMMENTS)
                .then()
                .statusCode(201)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("postId", equalTo(1))
                .body("name", equalTo("Test Comment"))
                .body("email", equalTo("test@example.com"))
                .body("body", equalTo("This is a test comment"));
    }

    /**
     * Test PUT /comments/1 endpoint
     * Tests updating an existing comment
     */
    @Test
    @DisplayName("Should update comment successfully")
    public void testUpdateComment() {
        String updatePayload = "{\n" +
                "  \"postId\": 1,\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Updated Comment\",\n" +
                "  \"email\": \"updated@example.com\",\n" +
                "  \"body\": \"This comment has been updated\"\n" +
                "}";

        given(requestSpec)
                .body(updatePayload)
                .when()
                .put(COMMENT_BY_ID_1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("id", equalTo(1))
                .body("name", equalTo("Updated Comment"))
                .body("email", equalTo("updated@example.com"));
    }

    /**
     * Test DELETE /comments/1 endpoint
     * Tests deleting a comment
     */
    @Test
    @DisplayName("Should delete comment successfully")
    public void testDeleteComment() {
        given(requestSpec)
                .when()
                .delete(COMMENT_BY_ID_1)
                .then()
                .statusCode(200);
    }
}
