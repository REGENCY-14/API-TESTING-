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
 * Test class for DELETE API endpoints
 * Tests resource deletion operations on JSONPlaceholder API
 */
@Feature("API Testing")
@Story("DELETE Endpoint Tests")
@DisplayName("DELETE API Tests")
public class DeleteTest extends BaseTest {

    private static final String POST_BY_ID_1 = Endpoints.POSTS + "/1";

    private static String postById(int postId) {
        return Endpoints.POSTS + "/" + postId;
    }

    /**
     * Tests the deletion of a post by ID.
     * Validates that the response status code is either 200 (OK) or 204 (No Content).
     */
    @Test
    @DisplayName("Should delete post successfully")

    public void testDeletePost() {
        given(requestSpec)
                .when()
            .delete(POST_BY_ID_1)
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }

    /**
     * Tests the deletion of a post by ID with response extraction.
     * Extracts the response and validates the status code.
     */
    @Test
    @DisplayName("Should validate deletion with response extraction")

    public void testDeletePostWithResponseValidation() {
        Response response = given(requestSpec)
                .when()
            .delete(POST_BY_ID_1)
                .then()
                .extract()
                .response();

        // Validate status code is either 200 (OK) or 204 (No Content)
        int statusCode = response.getStatusCode();
        assert (statusCode == 200 || statusCode == 204) : 
            "Status code should be 200 or 204, but got: " + statusCode;
    }

    /**
     * Tests the deletion of a post by ID with detailed validation.
     * Validates the response status code using assertion chaining.
     */
    @Test
    @DisplayName("Should delete post with logging and validation")

    public void testDeletePostWithDetailedValidation() {
        given(requestSpec)
                .when()
            .delete(POST_BY_ID_1)
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }

    /**
     * Tests the deletion of a post by ID using assertThat for validation.
     * Ensures the response status code is either 200 (OK) or 204 (No Content).
     */
    @Test
    @DisplayName("Should delete post with assertion chaining")

    public void testDeletePostWithAssertThat() {
        given(requestSpec)
                .when()
            .delete(POST_BY_ID_1)
                .then()
                .assertThat()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }

    /**
     * Tests the deletion of a post by ID and validates the success status.
     * Ensures the status code is in the 2xx range and specifically 200 or 204.
     */
    @Test
    @DisplayName("Should verify deletion returns success status")

    public void testDeletePostSuccessStatus() {
        Response response = given(requestSpec)
                .when()
            .delete(POST_BY_ID_1)
                .then()
                .extract()
                .response();

        int statusCode = response.getStatusCode();
        
        // Validate status code is in 2xx success range
        assert statusCode >= 200 && statusCode < 300 : 
            "Status code should be in 2xx range, but got: " + statusCode;
        
        // Specifically validate it's either 200 or 204
        assert (statusCode == 200 || statusCode == 204) : 
            "Status code should be 200 (OK) or 204 (No Content), but got: " + statusCode;
    }

    /**
     * Tests the deletion of multiple posts by their IDs.
     * Iterates through an array of post IDs, deleting each and validating the response.
     */
    @Test
    @DisplayName("Should delete different post resources")

    public void testDeleteMultiplePosts() {
        int[] postIds = {1, 2, 3};
        
        for (int postId : postIds) {
            Response response = given(requestSpec)
                    .when()
                    .delete(postById(postId))
                    .then()
                    .extract()
                    .response();
            
            int statusCode = response.getStatusCode();
            assert (statusCode == 200 || statusCode == 204) : 
                "Failed to delete post " + postId + ". Status: " + statusCode;
        }
    }
}
