package com.api.automation.tests;

import com.api.automation.base.BaseTest;
import com.api.automation.utils.SchemaUtil;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.*;

/**
 * Test class for GET API endpoints
 * Tests basic CRUD operations on JSONPlaceholder API
 */
@Feature("API Testing")
@Story("GET Endpoint Tests")
@DisplayName("GET API Tests")
public class GetPostTest extends BaseTest {

    /**
     * Test GET /posts/1 endpoint
     * Validates status code, content type, and response body structure
     */
    @Test
    @DisplayName("Should retrieve post with id 1")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetPost() {
        given(requestSpec)
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                .body("userId", notNullValue())
                .body("id", equalTo(1))
                .body("title", notNullValue())
                .body("body", notNullValue());
    }

    /**
     * Test GET /posts/1 endpoint with response validation
     * Demonstrates extracting and validating specific fields
     */
    @Test
    @DisplayName("Should validate post response fields")
    @Severity(SeverityLevel.HIGH)
    public void testGetPostWithResponseValidation() {
        Response response = given(requestSpec)
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .extract()
                .response();

        // Validate that required fields are present in response
        assert response.jsonPath().getString("userId") != null : "userId should not be null";
        assert response.jsonPath().getInt("id") == 1 : "id should be 1";
        assert response.jsonPath().getString("title") != null : "title should not be null";
        assert response.jsonPath().getString("body") != null : "body should not be null";
    }

    /**
     * Test GET /posts/1 endpoint with detailed assertion
     * Demonstrates logging and detailed validation
     */
    @Test
    @DisplayName("Should get post with all required fields")
    @Severity(SeverityLevel.HIGH)
    public void testGetPostWithDetailedValidation() {
        given(requestSpec)
                .when()
                .get("/posts/1")
                .then()
                .log().all()  // Log all response details
                .statusCode(200)
                .contentType(hasToString(containsString("application/json")))
                .body("userId", notNullValue(Integer.class))
                .body("id", notNullValue(Integer.class))
                .body("title", notNullValue(String.class))
                .body("body", notNullValue(String.class));
    }

    /**
     * Test GET /posts/1 endpoint with JSON schema validation
     * Validates response structure against post-schema.json
     */
    @Test
    @DisplayName("Should validate post response against JSON schema")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetPostWithJsonSchemaValidation() {
        given(requestSpec)
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body(matchesJsonSchema(SchemaUtil.getSchemaFile("post-schema.json")));
    }

    /**
     * Test GET /posts/1 endpoint with schema validation and detailed logging
     * Demonstrates complete validation including schema conformance
     */
    @Test
    @DisplayName("Should validate post with schema and logging")
    @Severity(SeverityLevel.HIGH)
    public void testGetPostWithSchemaAndLogging() {
        given(requestSpec)
                .when()
                .get("/posts/1")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body(matchesJsonSchema(SchemaUtil.getSchemaFile("post-schema.json")))
                .body("id", equalTo(1));
    }
}


    /**
     * Test GET /posts/1 endpoint
     * Validates status code, content type, and response body structure
     */
    @Test
    @DisplayName("Should retrieve post with id 1")
    public void testGetPost() {
        given(requestSpec)
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                .body("userId", notNullValue())
                .body("id", equalTo(1))
                .body("title", notNullValue())
                .body("body", notNullValue());
    }

    /**
     * Test GET /posts/1 endpoint with response validation
     * Demonstrates extracting and validating specific fields
     */
    @Test
    @DisplayName("Should validate post response fields")
    public void testGetPostWithResponseValidation() {
        Response response = given(requestSpec)
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .extract()
                .response();

        // Validate that required fields are present in response
        assert response.jsonPath().getString("userId") != null : "userId should not be null";
        assert response.jsonPath().getInt("id") == 1 : "id should be 1";
        assert response.jsonPath().getString("title") != null : "title should not be null";
        assert response.jsonPath().getString("body") != null : "body should not be null";
    }

    /**
     * Test GET /posts/1 endpoint with detailed assertion
     * Demonstrates logging and detailed validation
     */
    @Test
    @DisplayName("Should get post with all required fields")
    public void testGetPostWithDetailedValidation() {
        given(requestSpec)
                .when()
                .get("/posts/1")
                .then()
                .log().all()  // Log all response details
                .statusCode(200)
                .contentType(hasToString(containsString("application/json")))
                .body("userId", notNullValue(Integer.class))
                .body("id", notNullValue(Integer.class))
                .body("title", notNullValue(String.class))
                .body("body", notNullValue(String.class));
    }

    /**
     * Test GET /posts/1 endpoint with JSON schema validation
     * Validates response structure against post-schema.json
     */
    @Test
    @DisplayName("Should validate post response against JSON schema")
    public void testGetPostWithJsonSchemaValidation() {
        given(requestSpec)
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body(matchesJsonSchema(SchemaUtil.getSchemaFile("post-schema.json")));
    }

    /**
     * Test GET /posts/1 endpoint with schema validation and detailed logging
     * Demonstrates complete validation including schema conformance
     */
    @Test
    @DisplayName("Should validate post with schema and logging")
    public void testGetPostWithSchemaAndLogging() {
        given(requestSpec)
                .when()
                .get("/posts/1")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body(matchesJsonSchema(SchemaUtil.getSchemaFile("post-schema.json")))
                .body("id", equalTo(1));
    }
}

