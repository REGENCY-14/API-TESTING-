package com.api.automation.tests;

import com.api.automation.base.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Test class for PUT API endpoints
 * Tests resource update operations on JSONPlaceholder API
 */
@DisplayName("PUT API Tests")
public class PutTest extends BaseTest {

    /**
     * Test PUT /posts/1 endpoint
     * Validates status code is 200 and response contains updated data
     */
    @Test
    @DisplayName("Should update post successfully")
    public void testUpdatePost() {
        String requestBody = "{\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"updated title\",\n" +
                "  \"body\": \"updated body\",\n" +
                "  \"userId\": 1\n" +
                "}";

        given(requestSpec)
                .body(requestBody)
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("id", equalTo(1))
                .body("title", equalTo("updated title"))
                .body("body", equalTo("updated body"))
                .body("userId", equalTo(1));
    }

    /**
     * Test PUT /posts/1 endpoint with response extraction and validation
     * Validates updated data matches request
     */
    @Test
    @DisplayName("Should validate updated post with response extraction")
    public void testUpdatePostWithResponseValidation() {
        String requestBody = "{\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"updated title\",\n" +
                "  \"body\": \"updated body\",\n" +
                "  \"userId\": 1\n" +
                "}";

        Response response = given(requestSpec)
                .body(requestBody)
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200)
                .contentType(hasToString(containsString("application/json")))
                .extract()
                .response();

        // Validate response contains updated data
        assert response.jsonPath().getInt("id") == 1 : "id should be 1";
        assert response.jsonPath().getString("title").equals("updated title") : "title should be updated";
        assert response.jsonPath().getString("body").equals("updated body") : "body should be updated";
        assert response.jsonPath().getInt("userId") == 1 : "userId should remain 1";
    }

    /**
     * Test PUT /posts/1 endpoint with detailed logging and comprehensive assertions
     * Demonstrates full request/response logging and field validation
     */
    @Test
    @DisplayName("Should update post with all validations and logging")
    public void testUpdatePostWithDetailedValidation() {
        String requestBody = "{\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"updated title\",\n" +
                "  \"body\": \"updated body\",\n" +
                "  \"userId\": 1\n" +
                "}";

        given(requestSpec)
                .body(requestBody)
                .when()
                .put("/posts/1")
                .then()
                .log().all()  // Log complete response
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("id", notNullValue(Integer.class))
                .body("id", equalTo(1))
                .body("title", notNullValue(String.class))
                .body("title", equalTo("updated title"))
                .body("body", notNullValue(String.class))
                .body("body", equalTo("updated body"))
                .body("userId", notNullValue(Integer.class))
                .body("userId", equalTo(1));
    }

    /**
     * Test PUT /posts/1 endpoint with JSON content type validation
     * Demonstrates explicit Content-Type handling and assertion chaining
     */
    @Test
    @DisplayName("Should update post with JSON request body validation")
    public void testUpdatePostWithJsonValidation() {
        String payload = "{\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"updated title\",\n" +
                "  \"body\": \"updated body\",\n" +
                "  \"userId\": 1\n" +
                "}";

        given(requestSpec)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .put("/posts/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("title", equalTo("updated title"))
                .body("body", equalTo("updated body"))
                .body("userId", equalTo(1));
    }

    /**
     * Test PUT /posts/1 endpoint with field-by-field validation
     * Validates each updated field individually to ensure data integrity
     */
    @Test
    @DisplayName("Should validate each updated field")
    public void testUpdatePostFieldValidation() {
        String requestBody = "{\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"updated title\",\n" +
                "  \"body\": \"updated body\",\n" +
                "  \"userId\": 1\n" +
                "}";

        Response response = given(requestSpec)
                .body(requestBody)
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Detailed field validation
        int id = response.jsonPath().getInt("id");
        String title = response.jsonPath().getString("title");
        String body = response.jsonPath().getString("body");
        int userId = response.jsonPath().getInt("userId");

        // Assert each field
        assert id == 1 : "Post id should be 1";
        assert title.equals("updated title") : "Title should be 'updated title', but got: " + title;
        assert body.equals("updated body") : "Body should be 'updated body', but got: " + body;
        assert userId == 1 : "UserId should be 1";
    }
}
