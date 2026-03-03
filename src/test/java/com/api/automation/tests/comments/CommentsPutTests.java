package com.api.automation.tests.comments;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.api.automation.base.BaseTest;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import utils.Endpoints;

/**
 * Test class for Comments PUT endpoints
 * Tests PUT operations on JSONPlaceholder Comments API
 */
@Feature("API Testing")
@Story("Comments PUT Tests")
@DisplayName("Comments PUT API Tests")
public class CommentsPutTests extends BaseTest {

    // HTTP Request Methods
    
    private Response updateComment(int commentId, int postId, String name, String email, String body) {
        String payload = String.format("{\n" +
                "  \"postId\": %d,\n" +
                "  \"id\": %d,\n" +
                "  \"name\": \"%s\",\n" +
                "  \"email\": \"%s\",\n" +
                "  \"body\": \"%s\"\n" +
                "}", postId, commentId, name, email, body);
        return put(Endpoints.COMMENTS + "/" + commentId, payload);
    }

    // Test Methods

    /**
     * Test PUT /comments/1 endpoint
     * Tests updating an existing comment
     */
    @Test
    @DisplayName("Should update comment successfully")
    public void testUpdateComment() {
        updateComment(1, 1, "Updated Comment", "updated@example.com", "This comment has been updated")
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("id", equalTo(1))
                .body("name", equalTo("Updated Comment"))
                .body("email", equalTo("updated@example.com"));
    }
}
