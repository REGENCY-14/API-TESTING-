package com.api.automation.tests.posts;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.api.automation.base.BaseTest;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import utils.Endpoints;

import static com.api.automation.testdata.posts.PostsTestData.*;

/**
 * PUT tests for Posts API endpoints
 */
@Feature("API Testing")
@Story("Posts PUT Tests")
@DisplayName("Posts PUT API Tests")
public class PostsPutTests extends BaseTest {

    // HTTP Request Methods
    
    private Response updatePost(int postId, int userId, String title, String body) {
        String payload = String.format("{\n" +
                "  \"userId\": %d,\n" +
                "  \"id\": %d,\n" +
                "  \"title\": \"%s\",\n" +
                "  \"body\": \"%s\"\n" +
                "}", userId, postId, title, body);
        return put(Endpoints.POSTS + "/" + postId, payload);
    }

    // Test Methods

    /**
     * Test PUT /posts/1 endpoint
     * Tests updating an existing post
     */
    @Test
    @DisplayName("Should update post successfully")
    public void testUpdatePost() {
        updatePost(VALID_POST_ID, VALID_USER_ID, UPDATED_POST_TITLE, UPDATED_POST_BODY)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("id", equalTo(VALID_POST_ID))
                .body("title", equalTo(UPDATED_POST_TITLE))
                .body("body", equalTo(UPDATED_POST_BODY));
    }
}
