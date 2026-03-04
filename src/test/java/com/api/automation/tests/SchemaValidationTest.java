package com.api.automation.tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.api.automation.base.BaseTest;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;

/**
 * Schema Validation and Header Validation Tests
 * Validates JSON schema compliance and response headers for all API resources
 */
@Feature("API Testing")
@Story("Schema and Header Validation")
@DisplayName("Schema and Header Validation Tests")
public class SchemaValidationTest extends BaseTest {

    /**
     * Validates Posts resource against JSON schema and response headers
     */
    @Test
    @DisplayName("Should validate GET /posts/1 against post schema and headers")
    public void testPostSchemaValidation() {
        given(requestSpec)
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .header("Content-Type", notNullValue())
                .header("Connection", notNullValue())
                .body(matchesJsonSchemaInClasspath("schemas/post-schema.json"));
    }

    /**
     * Validates Albums resource against JSON schema and response headers
     */
    @Test
    @DisplayName("Should validate GET /albums/1 against album schema and headers")
    public void testAlbumSchemaValidation() {
        given(requestSpec)
                .when()
                .get("/albums/1")
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .header("Content-Type", notNullValue())
                .header("Connection", notNullValue())
                .body(matchesJsonSchemaInClasspath("schemas/album-schema.json"));
    }

    /**
     * Validates Users resource against JSON schema and response headers
     */
    @Test
    @DisplayName("Should validate GET /users/1 against user schema and headers")
    public void testUserSchemaValidation() {
        given(requestSpec)
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .header("Content-Type", notNullValue())
                .header("Connection", notNullValue())
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"));
    }

    /**
     * Validates Comments resource against JSON schema and response headers
     */
    @Test
    @DisplayName("Should validate GET /comments/1 against comment schema and headers")
    public void testCommentSchemaValidation() {
        given(requestSpec)
                .when()
                .get("/comments/1")
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .header("Content-Type", notNullValue())
                .header("Connection", notNullValue())
                .body(matchesJsonSchemaInClasspath("schemas/comment-schema.json"));
    }

    /**
     * Validates Photos resource against JSON schema and response headers
     */
    @Test
    @DisplayName("Should validate GET /photos/1 against photo schema and headers")
    public void testPhotoSchemaValidation() {
        given(requestSpec)
                .when()
                .get("/photos/1")
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .header("Content-Type", notNullValue())
                .header("Connection", notNullValue())
                .body(matchesJsonSchemaInClasspath("schemas/photo-schema.json"));
    }

    /**
     * Validates Todos resource against JSON schema and response headers
     */
    @Test
    @DisplayName("Should validate GET /todos/1 against todo schema and headers")
    public void testTodoSchemaValidation() {
        given(requestSpec)
                .when()
                .get("/todos/1")
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .header("Content-Type", notNullValue())
                .header("Connection", notNullValue())
                .body(matchesJsonSchemaInClasspath("schemas/todo-schema.json"));
    }

    /**
     * Validates response headers are present for all common headers
     */
    @Test
    @DisplayName("Should validate all common response headers are present")
    public void testCommonResponseHeaders() {
        given(requestSpec)
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .header("Content-Type", containsString("application/json; charset=utf-8"))
                .header("Connection", notNullValue())
                .header("X-Powered-By", notNullValue())
                .header("X-Content-Type-Options", notNullValue())
                .header("Etag", notNullValue())
                .header("Via", notNullValue())
                .header("Cache-Control", notNullValue());
    }

    /**
     * Validates CORS headers are properly configured
     */
    @Test
    @DisplayName("Should validate CORS headers are configured")
    public void testCorsHeaders() {
        given(requestSpec)
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .header("Access-Control-Allow-Credentials", notNullValue());
    }
}
