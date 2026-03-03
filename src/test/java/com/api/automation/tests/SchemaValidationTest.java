package com.api.automation.tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.api.automation.base.BaseTest;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Feature("API Testing")
@Story("Schema Validation")
@DisplayName("Schema Validation Tests")
public class SchemaValidationTest extends BaseTest {

    @Test
    @DisplayName("Should validate GET /posts/1 against post schema")
    public void testPostSchemaValidation() {
        given(requestSpec)
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/post-schema.json"));
    }
}
