package com.api.automation.tests.posts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.api.automation.base.BaseTest;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import utils.Endpoints;

import static com.api.automation.testdata.posts.PostsTestData.*;

/**
 * DELETE tests for Posts API endpoints
 */
@Feature("API Testing")
@Story("Posts DELETE Tests")
@DisplayName("Posts DELETE API Tests")
public class PostsDeleteTests extends BaseTest {

    // HTTP Request Methods
    
    private Response deletePost(int postId) {
        return delete(Endpoints.POSTS + "/" + postId);
    }

    // Test Methods

    /**
     * Test DELETE /posts/1 endpoint
     * Tests deleting a single post
     */
    @Test
    @DisplayName("Should delete post successfully")
    public void testDeletePost() {
        deletePost(VALID_POST_ID)
                .then()
                .statusCode(SUCCESS_STATUS_CODE);
    }
}
