package com.api.automation.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base class for REST Assured API automation tests
 * Provides common setup for request/response specifications and logging
 */
public class BaseTest {

    protected RequestSpecification requestSpec;
    private static final String BASE_URI = "https://jsonplaceholder.typicode.com";

    /**
     * Initialize RequestSpecification before each test
     * Sets up base URI, request/response logging, and content type
     */
    @BeforeEach
    public void setUp() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .setContentType("application/json")
                .build();
        
        RestAssured.requestSpecification = requestSpec;
    }

    /**
     * Get the current RequestSpecification
     *
     * @return RequestSpecification with base configuration
     */
    public RequestSpecification getRequestSpec() {
        return requestSpec;
    }

    /**
     * Get the base URI
     *
     * @return Base URI for API endpoints
     */
    public String getBaseUri() {
        return BASE_URI;
    }
}
