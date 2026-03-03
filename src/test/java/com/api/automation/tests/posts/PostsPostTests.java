package com.api.automation.tests.posts;

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

import static com.api.automation.testdata.posts.PostsTestData.*;

/**
 * POST tests for Posts API endpoints
 */
@Feature("API Testing")
@Story("Posts POST Tests")
@DisplayName("Posts POST API Tests")
public class PostsPostTests extends BaseTest {

    // HTTP Request Methods
    
    private Response createPost(int userId, String title, String body) {
        String payload = String.format("{\n" +
                "  \"userId\": %d,\n" +
                "  \"title\": \"%s\",\n" +
                "  \"body\": \"%s\"\n" +
                "}", userId, title, body);
        return post(Endpoints.POSTS, payload);
    }

    // Test Methods

    /**
     * Test POST /posts endpoint
     * Tests creating a new post
     */
    @Test
    @DisplayName("Should create a new post successfully")
    public void testCreatePost() {
        createPost(VALID_USER_ID, TEST_POST_TITLE, TEST_POST_BODY)
                .then()
                .statusCode(CREATED_STATUS_CODE)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("userId", equalTo(VALID_USER_ID))
                .body("title", equalTo(TEST_POST_TITLE))
                .body("body", equalTo(TEST_POST_BODY));
    }
}
