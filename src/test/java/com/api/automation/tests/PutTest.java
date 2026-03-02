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
 * Test class for PUT API endpoints
 * Tests resource update operations on JSONPlaceholder API
 */
@Feature("API Testing")
@Story("PUT Endpoint Tests")
@DisplayName("PUT API Tests")
public class PutTest extends BaseTest {

    private static final int POST_ID_1 = 1;
    private static final int USER_ID_1 = 1;
    private static final String UPDATED_TITLE = "updated title";
    private static final String UPDATED_BODY = "updated body";
    private static final String POST_BY_ID_1 = Endpoints.POSTS + "/1";
    private static final String UPDATE_POST_PAYLOAD = "{\n" +
            "  \"id\": " + POST_ID_1 + ",\n" +
            "  \"title\": \"" + UPDATED_TITLE + "\",\n" +
            "  \"body\": \"" + UPDATED_BODY + "\",\n" +
            "  \"userId\": " + USER_ID_1 + "\n" +
            "}";

    /**
     * Test PUT /posts/1 endpoint
     * Validates status code is 200 and response contains updated data
     */
    @Test
    @DisplayName("Should update post successfully")

    public void testUpdatePost() {
        given(requestSpec)
                .body(UPDATE_POST_PAYLOAD)
                .when()
                .put(POST_BY_ID_1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("id", equalTo(POST_ID_1))
                .body("title", equalTo(UPDATED_TITLE))
                .body("body", equalTo(UPDATED_BODY))
                .body("userId", equalTo(USER_ID_1));
    }

    /**
     * Test PUT /posts/1 endpoint with response extraction and validation
     * Validates updated data matches request
     */
    @Test
    @DisplayName("Should validate updated post with response extraction")

    public void testUpdatePostWithResponseValidation() {
        Response response = given(requestSpec)
                .body(UPDATE_POST_PAYLOAD)
                .when()
                .put(POST_BY_ID_1)
                .then()
                .statusCode(200)
                .contentType(hasToString(containsString("application/json")))
                .extract()
                .response();

        // Validate response contains updated data
        assert response.jsonPath().getInt("id") == POST_ID_1 : "id should be 1";
        assert response.jsonPath().getString("title").equals(UPDATED_TITLE) : "title should be updated";
        assert response.jsonPath().getString("body").equals(UPDATED_BODY) : "body should be updated";
        assert response.jsonPath().getInt("userId") == USER_ID_1 : "userId should remain 1";
    }

    /**
     * Test PUT /posts/1 endpoint with detailed logging and comprehensive assertions
     * Demonstrates full request/response logging and field validation
     */
    @Test
    @DisplayName("Should update post with all validations and logging")

    public void testUpdatePostWithDetailedValidation() {
        given(requestSpec)
                .body(UPDATE_POST_PAYLOAD)
                .when()
                .put(POST_BY_ID_1)
                .then()
                .log().all()  // Log complete response
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("id", notNullValue(Integer.class))
                .body("id", equalTo(POST_ID_1))
                .body("title", notNullValue(String.class))
                .body("title", equalTo(UPDATED_TITLE))
                .body("body", notNullValue(String.class))
                .body("body", equalTo(UPDATED_BODY))
                .body("userId", notNullValue(Integer.class))
                .body("userId", equalTo(USER_ID_1));
    }

    /**
     * Test PUT /posts/1 endpoint with JSON content type validation
     * Demonstrates explicit Content-Type handling and assertion chaining
     */
    @Test
    @DisplayName("Should update post with JSON request body validation")

    public void testUpdatePostWithJsonValidation() {
        given(requestSpec)
                .contentType(ContentType.JSON)
                .body(UPDATE_POST_PAYLOAD)
                .when()
                .put(POST_BY_ID_1)
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(POST_ID_1))
                .body("title", equalTo(UPDATED_TITLE))
                .body("body", equalTo(UPDATED_BODY))
                .body("userId", equalTo(USER_ID_1));
    }

    /**
     * Test PUT /posts/1 endpoint with field-by-field validation
     * Validates each updated field individually to ensure data integrity
     */
    @Test
    @DisplayName("Should validate each updated field")

    public void testUpdatePostFieldValidation() {
        Response response = given(requestSpec)
                .body(UPDATE_POST_PAYLOAD)
                .when()
                .put(POST_BY_ID_1)
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
        assert id == POST_ID_1 : "Post id should be 1";
        assert title.equals(UPDATED_TITLE) : "Title should be 'updated title', but got: " + title;
        assert body.equals(UPDATED_BODY) : "Body should be 'updated body', but got: " + body;
        assert userId == USER_ID_1 : "UserId should be 1";
    }
}
