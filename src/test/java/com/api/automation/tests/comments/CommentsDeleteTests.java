package com.api.automation.tests.comments;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.api.automation.base.BaseTest;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import utils.Endpoints;

/**
 * Test class for Comments DELETE endpoints
 * Tests DELETE operations on JSONPlaceholder Comments API
 */
@Feature("API Testing")
@Story("Comments DELETE Tests")
@DisplayName("Comments DELETE API Tests")
public class CommentsDeleteTests extends BaseTest {

    // HTTP Request Methods
    
    private Response deleteComment(int commentId) {
        return delete(Endpoints.COMMENTS + "/" + commentId);
    }

    // Test Methods

    /**
     * Test DELETE /comments/1 endpoint
     * Tests deleting a comment
     */
    @Test
    @DisplayName("Should delete comment successfully")
    public void testDeleteComment() {
        deleteComment(1)
                .then()
                .statusCode(200);
    }
}
