package com.api.automation.tests;

import com.api.automation.base.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    /**
     * Test POST /posts endpoint
     * Validates status code is 201 and response contains expected fields
     */
    @Test
    @DisplayName("Should create a new post successfully")

    public void testCreatePost() {
        String requestBody = "{\n" +
                "  \"title\": \"foo\",\n" +
                "  \"body\": \"bar\",\n" +
                "  \"userId\": 1\n" +
                "}";

        given(requestSpec)
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("title", equalTo("foo"))
                .body("body", equalTo("bar"))
                .body("userId", equalTo(1));
    }

    /**
     * Test POST /posts endpoint with request body validation
     * Uses Map-based request body and validates response fields
     */
    @Test
    @DisplayName("Should validate post creation with response extraction")

    public void testCreatePostWithResponseValidation() {
        String requestBody = "{\n" +
                "  \"title\": \"foo\",\n" +
                "  \"body\": \"bar\",\n" +
                "  \"userId\": 1\n" +
                "}";

        Response response = given(requestSpec)
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .contentType(hasToString(containsString("application/json")))
                .extract()
                .response();

        // Validate response body matches request
        assert response.jsonPath().getInt("userId") == 1 : "userId should be 1";
        assert response.jsonPath().getString("title").equals("foo") : "title should match request";
        assert response.jsonPath().getString("body").equals("bar") : "body should match request";
        assert response.jsonPath().getInt("id") > 0 : "id should be a positive integer";
    }

    /**
     * Test POST /posts endpoint with detailed logging
     * Demonstrates full request/response logging and comprehensive assertions
     */
    @Test
    @DisplayName("Should create post with all validations and logging")

    public void testCreatePostWithDetailedValidation() {
        String requestBody = "{\n" +
                "  \"title\": \"foo\",\n" +
                "  \"body\": \"bar\",\n" +
                "  \"userId\": 1\n" +
                "}";

        given(requestSpec)
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .log().all()  // Log complete response
                .statusCode(201)
                .contentType(containsString("application/json"))
                .body("id", notNullValue(Integer.class))
                .body("title", notNullValue(String.class))
                .body("title", equalTo("foo"))
                .body("body", notNullValue(String.class))
                .body("body", equalTo("bar"))
                .body("userId", notNullValue(Integer.class))
                .body("userId", equalTo(1));
    }

    /**
     * Test POST /posts endpoint with JSON request compilation
     * Demonstrates building JSON payload programmatically
     */
    @Test
    @DisplayName("Should create post with JSON request body validation")

    public void testCreatePostWithJsonValidation() {
        // Request payload
        String payload = "{\n" +
                "  \"title\": \"foo\",\n" +
                "  \"body\": \"bar\",\n" +
                "  \"userId\": 1\n" +
                "}";

        given(requestSpec)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/posts")
                .then()
                .assertThat()
                .statusCode(201)
                .body("userId", equalTo(1))
                .body("title", equalTo("foo"))
                .body("body", equalTo("bar"))
                .body("id", notNullValue());
    }
}
