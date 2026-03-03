package com.api.automation.pages;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Base page class for all API page objects
 * Provides common functionality for API interactions
 */
public class BasePage {
    
    protected RequestSpecification requestSpec;
    
    /**
     * Constructor to initialize the page with request specification
     * @param requestSpec RequestSpecification object for making API calls
     */
    public BasePage(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }
    
    /**
     * Makes a GET request to the specified endpoint
     * @param endpoint The endpoint path
     * @return Response object containing the API response
     */
    protected Response get(String endpoint) {
        return RestAssured.given(requestSpec)
                .when()
                .get(endpoint);
    }
    
    /**
     * Makes a GET request with query parameters
     * @param endpoint The endpoint path
     * @param queryParam The query parameter name
     * @param value The query parameter value
     * @return Response object containing the API response
     */
    protected Response get(String endpoint, String queryParam, Object value) {
        return RestAssured.given(requestSpec)
                .queryParam(queryParam, value)
                .when()
                .get(endpoint);
    }
    
    /**
     * Makes a POST request with a body
     * @param endpoint The endpoint path
     * @param body The request body
     * @return Response object containing the API response
     */
    protected Response post(String endpoint, String body) {
        return RestAssured.given(requestSpec)
                .body(body)
                .when()
                .post(endpoint);
    }
    
    /**
     * Makes a PUT request with a body
     * @param endpoint The endpoint path
     * @param body The request body
     * @return Response object containing the API response
     */
    protected Response put(String endpoint, String body) {
        return RestAssured.given(requestSpec)
                .body(body)
                .when()
                .put(endpoint);
    }
    
    /**
     * Makes a PATCH request with a body
     * @param endpoint The endpoint path
     * @param body The request body
     * @return Response object containing the API response
     */
    protected Response patch(String endpoint, String body) {
        return RestAssured.given(requestSpec)
                .body(body)
                .when()
                .patch(endpoint);
    }
    
    /**
     * Makes a DELETE request
     * @param endpoint The endpoint path
     * @return Response object containing the API response
     */
    protected Response delete(String endpoint) {
        return RestAssured.given(requestSpec)
                .when()
                .delete(endpoint);
    }
}
