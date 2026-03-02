package com.api.automation.tests;

import com.api.automation.base.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.Endpoints;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Test class for POST API endpoints
 * Tests resource creation on JSONPlaceholder API
 */
@Feature("API Testing")
@Story("POST Endpoint Tests")
@DisplayName("POST API Tests")
public class PostTest extends BaseTest {

    private static final String TITLE_FOO = "foo";
    private static final String BODY_BAR = "bar";
    private static final int USER_ID_1 = 1;
    private static final String CREATE_POST_PAYLOAD = "{\n" +
        "  \"title\": \"" + TITLE_FOO + "\",\n" +
        "  \"body\": \"" + BODY_BAR + "\",\n" +
        "  \"userId\": " + USER_ID_1 + "\n" +
        "}";

    /**
     * Tests creating a new post.
     * Validates that the response contains the created post data and has a 201 status code.
     */
    @Test
    @DisplayName("Should create a new post successfully")

    public void testCreatePost() {
        given(requestSpec)
                .body(CREATE_POST_PAYLOAD)
                .when()
                .post(Endpoints.POSTS)
                .then()
                .statusCode(201)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("title", equalTo(TITLE_FOO))
                .body("body", equalTo(BODY_BAR))
                .body("userId", equalTo(USER_ID_1));
    }

    /**
     * Tests creating a new post with response extraction.
     * Extracts the response and validates that the created data matches the request.
     */
    @Test
    @DisplayName("Should validate post creation with response extraction")

    public void testCreatePostWithResponseValidation() {
        Response response = given(requestSpec)
                .body(CREATE_POST_PAYLOAD)
                .when()
                .post(Endpoints.POSTS)
                .then()
                .statusCode(201)
                .contentType(hasToString(containsString("application/json")))
                .extract()
                .response();

        // Validate response body matches request
        assert response.jsonPath().getInt("userId") == USER_ID_1 : "userId should be 1";
        assert response.jsonPath().getString("title").equals(TITLE_FOO) : "title should match request";
        assert response.jsonPath().getString("body").equals(BODY_BAR) : "body should match request";
        assert response.jsonPath().getInt("id") > 0 : "id should be a positive integer";
    }

    /**
     * Tests creating a new post with detailed validation.
     * Validates the response status code, content type, and created fields.
     */
    @Test
    @DisplayName("Should create post with all validations and logging")

    public void testCreatePostWithDetailedValidation() {
        given(requestSpec)
                .body(CREATE_POST_PAYLOAD)
                .when()
                .post(Endpoints.POSTS)
                .then()
                .statusCode(201)
                .contentType(containsString("application/json"))
                .body("id", notNullValue(Integer.class))
                .body("title", notNullValue(String.class))
                .body("title", equalTo(TITLE_FOO))
                .body("body", notNullValue(String.class))
                .body("body", equalTo(BODY_BAR))
                .body("userId", notNullValue(Integer.class))
                .body("userId", equalTo(USER_ID_1));
    }

    /**
     * Tests creating a new post with JSON request compilation.
     * Demonstrates building JSON payload programmatically.
     */
    @Test
    @DisplayName("Should create post with JSON request body validation")

    public void testCreatePostWithJsonValidation() {
        given(requestSpec)
                .contentType(ContentType.JSON)
                .body(CREATE_POST_PAYLOAD)
                .when()
                .post(Endpoints.POSTS)
                .then()
                .assertThat()
                .statusCode(201)
                .body("userId", equalTo(USER_ID_1))
                .body("title", equalTo(TITLE_FOO))
                .body("body", equalTo(BODY_BAR))
                .body("id", notNullValue());
    }
}
