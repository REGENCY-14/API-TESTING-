package com.api.automation.tests;

import com.api.automation.base.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    /**
     * Test DELETE /posts/1 endpoint
     * Validates status code is 200 or 204 indicating successful deletion
     */
    @Test
    @DisplayName("Should delete post successfully")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeletePost() {
        given(requestSpec)
                .when()
                .delete("/posts/1")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }

    /**
     * Test DELETE /posts/1 endpoint with response extraction
     * Validates response status and captures response details
     */
    @Test
    @DisplayName("Should validate deletion with response extraction")
    @Severity(SeverityLevel.HIGH)
    public void testDeletePostWithResponseValidation() {
        Response response = given(requestSpec)
                .when()
                .delete("/posts/1")
                .then()
                .extract()
                .response();

        // Validate status code is either 200 (OK) or 204 (No Content)
        int statusCode = response.getStatusCode();
        assert (statusCode == 200 || statusCode == 204) : 
            "Status code should be 200 or 204, but got: " + statusCode;
    }

    /**
     * Test DELETE /posts/1 endpoint with detailed logging
     * Demonstrates full request/response logging and comprehensive validation
     */
    @Test
    @DisplayName("Should delete post with logging and validation")
    @Severity(SeverityLevel.HIGH)
    public void testDeletePostWithDetailedValidation() {
        given(requestSpec)
                .when()
                .delete("/posts/1")
                .then()
                .log().all()  // Log complete response
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }

    /**
     * Test DELETE /posts/1 endpoint with assertion chaining
     * Uses assertThat() for chainable assertions
     */
    @Test
    @DisplayName("Should delete post with assertion chaining")
    @Severity(SeverityLevel.MEDIUM)
    public void testDeletePostWithAssertThat() {
        given(requestSpec)
                .when()
                .delete("/posts/1")
                .then()
                .assertThat()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }

    /**
     * Test DELETE /posts/1 endpoint with response status code check
     * Validates response is successful but does not exceed 299 (2xx range)
     */
    @Test
    @DisplayName("Should verify deletion returns success status")
    @Severity(SeverityLevel.HIGH)
    public void testDeletePostSuccessStatus() {
        Response response = given(requestSpec)
                .when()
                .delete("/posts/1")
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
     * Test DELETE /posts/1 endpoint with multiple resource deletion
     * Demonstrates deleting different post IDs using parametrized approach
     */
    @Test
    @DisplayName("Should delete different post resources")
    @Severity(SeverityLevel.MEDIUM)
    public void testDeleteMultiplePosts() {
        int[] postIds = {1, 2, 3};
        
        for (int postId : postIds) {
            Response response = given(requestSpec)
                    .when()
                    .delete("/posts/" + postId)
                    .then()
                    .extract()
                    .response();
            
            int statusCode = response.getStatusCode();
            assert (statusCode == 200 || statusCode == 204) : 
                "Failed to delete post " + postId + ". Status: " + statusCode;
        }
    }
}
