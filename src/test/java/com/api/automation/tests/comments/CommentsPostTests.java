package com.api.automation.tests.comments;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.api.automation.base.BaseTest;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import utils.Endpoints;

/**
 * Test class for Comments POST endpoints
 * Tests POST operations on JSONPlaceholder Comments API
 */
@Feature("API Testing")
@Story("Comments POST Tests")
@DisplayName("Comments POST API Tests")
public class CommentsPostTests extends BaseTest {

    // HTTP Request Methods
    
    private Response createComment(int postId, String name, String email, String body) {
        String payload = String.format("{\n" +
                "  \"postId\": %d,\n" +
                "  \"name\": \"%s\",\n" +
                "  \"email\": \"%s\",\n" +
                "  \"body\": \"%s\"\n" +
                "}", postId, name, email, body);
        return post(Endpoints.COMMENTS, payload);
    }

    // Test Methods

    /**
     * Test POST /comments endpoint
     * Tests creating a new comment
     */
    @Test
    @DisplayName("Should create a new comment successfully")
    public void testCreateComment() {
        createComment(1, "Test Comment", "test@example.com", "This is a test comment")
                .then()
                .statusCode(201)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("postId", equalTo(1))
                .body("name", equalTo("Test Comment"))
                .body("email", equalTo("test@example.com"))
                .body("body", equalTo("This is a test comment"));
    }
}
